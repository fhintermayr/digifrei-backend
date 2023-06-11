package de.icp.match.request.service;

import de.icp.match.request.model.ExemptionRequest;
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

        String subject = String.format("Neue Dienstbefreiung von %s %s", applicant.getFirstName(), applicant.getLastName());
        String textTemplate = """
                Hallo %s %s,

                %s %s hat soeben einen Dienstbefreiungsantrag vom %s bis zum %s beantragt.

                Mit freundlichen Grüßen
                Die DigiFrei Plattform
                """;

        String emailText = String.format(textTemplate,
                emailRecipient.getFirstName(),
                emailRecipient.getLastName(),
                applicant.getFirstName(),
                applicant.getLastName(),
                submission.getStartTime(),
                submission.getEndTime());

        sendSimpleMessage(emailRecipient.getEmail(), subject, textTemplate);
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
