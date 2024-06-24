package eofe.springboot3.record;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@JsonTest
public class POSTransactionTest {

    @Autowired
    private JacksonTester<POSTransaction> json;

    @Test
    void shouldSerializePOSTransaction() throws IOException {
        POSTransaction tx = new POSTransaction(UUID.fromString("019027f1-375c-7596-b79c-a521783df670"), new BigDecimal("15.29"));

        var jsonFile = ResourceUtils.getFile("classpath:pos-transaction.json");
        assertThat(json.write(tx)).isStrictlyEqualToJson(jsonFile);

        assertThat(json.write(tx)).hasJsonPath("@.id");

        String jsonUUID = JsonPath.read(jsonFile, "@.id");
        assertThat(json.write(tx)).extractingJsonPathValue("@.id").isEqualTo(jsonUUID);

        assertThat(json.write(tx)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(tx)).extractingJsonPathNumberValue("@.amount").isEqualTo(15.29);
    }

    @Test
    void shouldDeserializeTransaction() throws IOException {
        String given ="""
                {
                  "id": "019027f1-375c-7596-b79c-a521783df670",
                  "amount": 6.99
                }
                """;
        var uuid = UUID.fromString("019027f1-375c-7596-b79c-a521783df670");


        assertThat(json.parse(given)).isEqualTo(new POSTransaction(UUID.fromString("019027f1-375c-7596-b79c-a521783df670"), new BigDecimal("6.99")));
        assertThat(json.parseObject(given).id()).isEqualTo(uuid);
        //
        assertThat(json.parseObject(given).amount()).isEqualTo(new BigDecimal("6.99"));
    }
}
