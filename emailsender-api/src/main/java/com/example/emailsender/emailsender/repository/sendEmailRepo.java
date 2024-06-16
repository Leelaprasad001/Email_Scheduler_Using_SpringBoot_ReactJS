package com.example.emailsender.emailsender.repository;
import com.example.emailsender.emailsender.model.sendEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface sendEmailRepo extends JpaRepository<sendEmail, Long> {
}