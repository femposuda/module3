package ru.masha;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CourierTestArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(new Courier("", "123", "name")),
                Arguments.of(new Courier("qwerty", "", "name"))
                //Arguments.of(new Courier("tryeee", "4343", ""))
        );
    }
}
