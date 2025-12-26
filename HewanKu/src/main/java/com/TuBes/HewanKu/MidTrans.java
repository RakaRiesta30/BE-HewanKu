// package com.TuBes.HewanKu;

// import java.util.HashMap;
// import java.util.Map;

// import com.midtrans.Midtrans;
// import com.midtrans.httpclient.SnapApi;

// import jakarta.annotation.PostConstruct;

// public class MidTrans {
//     @PostConstruct
//     public void init() {
//         Midtrans.serverKey = "Mid-server-PyYb_KwMrcp7-jFltKOqj4jz";
//         Midtrans.isProduction = false;
//     }

//     public String createPayment(Long orderId, Long amount) throws Exception {
//         Map<String, Object> params = new HashMap<>();
//         Map<String, String> transactionDetails = new HashMap<>();
//         transactionDetails.put("order_id", "ORDER-" + orderId);
//         transactionDetails.put("gross_amount", amount.toString());
//         params.put("transaction_details", transactionDetails);
//         String snapToken = SnapApi.createTransactionToken(params);
//         return snapToken;
//     }
// }
