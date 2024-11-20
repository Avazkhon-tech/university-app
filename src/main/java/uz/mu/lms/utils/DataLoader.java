package uz.mu.lms.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.mu.lms.model.Student;
import uz.mu.lms.repository.StudentRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {

        Optional<Student> byUserUsername = studentRepository.findByUser_Username("avazkhonnazirov33@gmail.com");
        if (byUserUsername.isPresent()) {
            System.out.println(byUserUsername.get());
        } else {
            System.out.println("User not found");
        }

    }
}
