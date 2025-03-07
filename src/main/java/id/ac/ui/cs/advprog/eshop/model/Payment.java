package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.method = method;
        this.paymentData = paymentData;
        this.status = validatePayment(method, paymentData);
    }

    private static final String PAYMENT_METHOD_VOUCHER = "Voucher Code";
    private static final String PAYMENT_METHOD_COD = "Cash on Delivery";

    private String validatePayment(String method, Map<String, String> paymentData) {
        if (PAYMENT_METHOD_VOUCHER.equals(method)) {
            return validateVoucherCode(paymentData.get("voucherCode"));
        }
        if (PAYMENT_METHOD_COD.equals(method)) {
            return validateCashOnDelivery(paymentData);
        }
        return "REJECTED";
    }

    private String validateVoucherCode(String voucherCode) {
        if (voucherCode == null) return "REJECTED";
        if (voucherCode.length() != 16) return "REJECTED";
        if (!voucherCode.startsWith("ESHOP")) return "REJECTED";
        if (voucherCode.replaceAll("\\D", "").length() != 8) return "REJECTED";

        return "SUCCESS";
    }

    private String validateCashOnDelivery(Map<String, String> paymentData) {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (address == null || address.isEmpty() || deliveryFee == null || deliveryFee.isEmpty()) {
            return "REJECTED";
        }
        return "SUCCESS";
    }

    public void setStatus(String status) {
        if ("SUCCESS".equals(status) || "REJECTED".equals(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status");
        }
    }
}