package eofe.springboot3.record;

import java.math.BigDecimal;
import java.util.UUID;

public record POSTransaction(UUID id, BigDecimal amount) {
}
