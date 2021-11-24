package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.TransactionDto;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser<TransactionDto> parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(new ValidatorImpl());
    }

    @Test
    public void parseLine_ok() {
        List<String> lines = List.of("type,fruit,quantity", "b,apple,100");
        String expected = new TransactionDto("b", "apple", 100).toString();
        String actual = parser.parse(lines).get(0).toString();
        assertEquals(expected,actual);
    }

    @Test(expected = ValidationException.class)
    public void parseLine_invalidQuantity_NotOk() {
        parser.parse(List.of("type,fruit,quantity", "b,apple,-100"));
    }

    @Test(expected = ValidationException.class)
    public void parseLine_invalidFruit_NotOk() {
        parser.parse(List.of("type,fruit,quantity", "b,Apple,100"));
    }

    @Test(expected = ValidationException.class)
    public void parseLine_invalidType_NotOk() {
        parser.parse(List.of("type,fruit,quantity", "B,apple,100"));
    }
}