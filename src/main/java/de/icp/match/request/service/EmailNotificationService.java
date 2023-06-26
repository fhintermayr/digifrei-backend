package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.model.ProcessingStatus;
import de.icp.match.request.util.GermanDateFormatter;
import de.icp.match.user.model.Apprentice;
import de.icp.match.user.model.EmailRecipient;
import de.icp.match.user.model.SocioEduExpert;
import de.icp.match.user.model.Trainer;
import de.icp.match.user.service.UserQueryService;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailNotificationService {

    private final UserQueryService userQueryService;
    private final JavaMailSender mailSender;
    private final RequestQueryService requestQueryService;
    private final String mailSenderAddress;
    private final RequestUpdateService requestUpdateService;

    public EmailNotificationService(UserQueryService userQueryService, JavaMailSender getJavaMailSender, Environment environment, RequestQueryService requestQueryService, RequestUpdateService requestUpdateService) {
        this.userQueryService = userQueryService;
        this.mailSender = getJavaMailSender;
        this.requestQueryService = requestQueryService;
        this.mailSenderAddress = environment.getProperty("spring.mail.properties.sender.address");
        this.requestUpdateService = requestUpdateService;
    }

    public void notifyAboutSubmission(ExemptionRequest submission) {

        List<EmailRecipient> emailRecipients = getEmailRecipientsOfSubmission(submission);

        emailRecipients.forEach(emailRecipient -> sendSubmissionNotificationTo(emailRecipient, submission));
    }

    @Scheduled(cron = "${mail.reminder.cron}", zone = "Europe/Berlin")
    public void sendDailyConfirmationReminders() {

        List<ExemptionRequest> requestsWithMissingConfirmation = requestQueryService.getRequestsWithMissingConfirmation();

        requestsWithMissingConfirmation.forEach(this::notifyAboutMissingConfirmationAndUpdateProcessingStatus);
    }

    private void notifyAboutMissingConfirmationAndUpdateProcessingStatus(ExemptionRequest exemptionRequest) {

        Apprentice applicant = exemptionRequest.getApplicant();
        List<Trainer> trainersOfApplicant = userQueryService.getTrainersOfDepartment(applicant.getDepartment().getId());

        notifyApplicantAboutMissingConfirmation(exemptionRequest);
        trainersOfApplicant.forEach(trainer -> notifyTrainerAboutMissingConfirmation(trainer, exemptionRequest));

        requestUpdateService.updateProcessingStatusById(exemptionRequest.getId(), ProcessingStatus.CONFIRMATION_MISSING);
    }

    private void notifyApplicantAboutMissingConfirmation(ExemptionRequest exemptionRequest) {

        Apprentice applicant = exemptionRequest.getApplicant();

        String subject = "Fehlende Abwesenheitsbestätigung für Dienstbefreiung";

        String formattedStartDate =  GermanDateFormatter.localDateTimeToGermanDateTime(exemptionRequest.getStartTime());
        String formattedEndDate =  GermanDateFormatter.localDateTimeToGermanDateTime(exemptionRequest.getEndTime());

        String textTemplate = """
                Hallo %s %s,

                du wurdest in der Vergangenheit wegen folgender Dienstbefreiung freigestellt.
                
                Von: %s Uhr
                Bis: %s Uhr
                Begründung: %s
                
                Allerdings liegt bisher keine Abwesenheitsbestätigung vor. Bitte reiche diese zeitnah bei deinen Ausbilder nach.

                Mit freundlichen Grüßen
                Die DigiFrei Plattform
                """;

        String emailText = String.format(textTemplate,
                applicant.getFirstName(),
                applicant.getLastName(),
                formattedStartDate,
                formattedEndDate,
                exemptionRequest.getReason());

        sendSimpleMessage(applicant.getEmail(), subject, emailText);
    }

    private void notifyTrainerAboutMissingConfirmation(Trainer trainer, ExemptionRequest exemptionRequest) {

        Apprentice applicant = exemptionRequest.getApplicant();

        String subject = "Fehlende Abwesenheitsbestätigung für Dienstbefreiung";

        String formattedStartDate =  GermanDateFormatter.localDateTimeToGermanDateTime(exemptionRequest.getStartTime());
        String formattedEndDate =  GermanDateFormatter.localDateTimeToGermanDateTime(exemptionRequest.getEndTime());

        String textTemplate = """
                Hallo %s %s,

                %s %s wurde in der Vergangenheit wegen folgender Dienstbefreiung freigestellt.
                
                Von: %s Uhr
                Bis: %s Uhr
                Begründung: %s
                
                Allerdings liegt bisher keine Abwesenheitsbestätigung vor. Bitte erinnere den/die Teilnehmer/in diese nachzureichen.

                Mit freundlichen Grüßen
                Die DigiFrei Plattform
                """;

        String emailText = String.format(textTemplate,
                trainer.getFirstName(),
                trainer.getLastName(),
                applicant.getFirstName(),
                applicant.getLastName(),
                formattedStartDate,
                formattedEndDate,
                exemptionRequest.getReason());

        sendSimpleMessage(trainer.getEmail(), subject, emailText);
    }

    private List<EmailRecipient> getEmailRecipientsOfSubmission(ExemptionRequest submission) {

        Apprentice applicant = submission.getApplicant();

        List<Trainer> trainersOfApplicant = userQueryService.getTrainersOfDepartment(applicant.getDepartment().getId());
        SocioEduExpert socioEduExpertOfApplicant = applicant.getSocioEduExpert();

        List<EmailRecipient> recipients = new ArrayList<>();
        recipients.add(socioEduExpertOfApplicant);
        recipients.addAll(trainersOfApplicant);

        return recipients;
    }

    private void sendSubmissionNotificationTo(EmailRecipient emailRecipient, ExemptionRequest submission) {

        Apprentice applicant = submission.getApplicant();

        String formattedStartDate =  GermanDateFormatter.localDateTimeToGermanDateTime(submission.getStartTime());
        String formattedEndDate =  GermanDateFormatter.localDateTimeToGermanDateTime(submission.getEndTime());

        String subject = String.format("Neuer Dienstbefreiungsantrag von %s %s", applicant.getFirstName(), applicant.getLastName());
        String textTemplate = """
                Hallo %s %s,

                %s %s hat soeben eine Dienstbefreiung beantragt.
                
                Von: %s Uhr
                Bis: %s Uhr
                Begründung: %s

                Mit freundlichen Grüßen
                Die DigiFrei Plattform
                """;

        String emailText = String.format(textTemplate,
                emailRecipient.getFirstName(),
                emailRecipient.getLastName(),
                applicant.getFirstName(),
                applicant.getLastName(),
                formattedStartDate,
                formattedEndDate,
                submission.getReason());

        sendSimpleMessage(emailRecipient.getEmail(), subject, emailText);
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSenderAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }


}
