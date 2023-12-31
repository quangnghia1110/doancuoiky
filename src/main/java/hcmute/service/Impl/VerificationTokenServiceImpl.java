package hcmute.service.Impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import hcmute.model.VerificationToken;
import hcmute.model.user.User;
import hcmute.repository.PasswordResetTokenRepository;
import hcmute.repository.VerificationTokenRepository;
import hcmute.service.IVerificationTokenService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VerificationTokenServiceImpl implements IVerificationTokenService {

	@Autowired
	VerificationTokenRepository tokenRepository;

	@Autowired
	PasswordResetTokenRepository passwordTokenRepository;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public VerificationToken getVerificationToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public String sendVerificationToken(HttpServletRequest request, VerificationToken newToken,User user) {
		try {
			final String confirmationUrl ="http://localhost:8081" + "/auth/register-confirm?token=" + newToken.getToken();
            final SimpleMailMessage email = constructVerificationTokenEmail(confirmationUrl, user);
            mailSender.send(email);
        } catch (final MailAuthenticationException e) {
            log.debug("MailAuthenticationException", e);
            return "redirect:/emailError";
        } catch (final Exception e) {
            return "redirect:/login";
        }
		return null;
		
	}
	
   private SimpleMailMessage constructVerificationTokenEmail(final String resetURL, final User user) {
        
        final String subject = "Xác nhận đăng ký";
		final String message = messages.getMessage("message.regSuccLink", null,
				"Xin chào "+user.getFirstName()+" "+user.getLastName()+","
				+"\n\nChúc mừng bạn đã đăng ký tài khoản thành công. Vui lòng nhấn vào link bên dưới để xác thực tài khoản.",null);
		final String endingMessage = "Thân chào";
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject(subject);
		email.setText(message + " \r\n" + resetURL +"\n\n\n\n"+endingMessage);
        return email;
    }

	@Override
	public VerificationToken getTokenByUser(User savedUser) {		
		return tokenRepository.getTokenByUser(savedUser);
	}

	
	

}
