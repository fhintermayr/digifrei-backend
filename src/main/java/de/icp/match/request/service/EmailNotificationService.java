package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionRequest;
import de.icp.match.request.util.GermanDateFormatter;
import de.icp.match.user.model.Apprentice;
import de.icp.match.user.model.EmailRecipient;
import de.icp.match.user.model.SocioEduExpert;
import de.icp.match.user.model.Trainer;
import de.icp.match.user.service.UserQueryService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailNotificationService {

    private final UserQueryService userQueryService;
    private final JavaMailSender mailSender;

    public EmailNotificationService(UserQueryService userQueryService, JavaMailSender getJavaMailSender) {
        this.userQueryService = userQueryService;
        this.mailSender = getJavaMailSender;
    }

    public void notifyAboutSubmission(ExemptionRequest submission) {

        List<EmailRecipient> emailRecipients = getEmailRecipientsOfSubmission(submission);

        emailRecipients.forEach(emailRecipient -> sendSubmissionNotificationTo(emailRecipient, submission));
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
        message.setFrom("xxx@xxx.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }


}
