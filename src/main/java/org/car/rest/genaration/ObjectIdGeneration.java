package org.car.rest.genaration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@PropertySource("classpath:fill-configuration.properties")
public class ObjectIdGeneration {
    private final Random random = new Random();
    private final int count;

    public ObjectIdGeneration(@Value("${charsInObjectId}") int count) {
        this.count = count;
    }

    public String generateRandomChars() {
        StringBuilder stringBuilder = new StringBuilder();

        char currentChar;

        for (int index = 0; index < count; index++) {

            if (random.nextBoolean()) {
                currentChar = (char) (random.nextInt('Z' - 'A' + 1) + 'A');
            } else {
                currentChar = (char) (random.nextInt('z' - 'a' + 1) + 'a');
            }

            stringBuilder.append(currentChar);
        }

        return stringBuilder.toString();
    }

}
