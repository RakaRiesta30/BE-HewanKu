package com.TuBes.HewanKu.Pesanan;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.BaseResponse;
import com.TuBes.HewanKu.Hewan.Hewan;
import com.TuBes.HewanKu.Hewan.HewanRepository;
import com.TuBes.HewanKu.KirimEmailPesan;
import com.TuBes.HewanKu.Pengguna.PenggunaRepository;
import com.TuBes.HewanKu.Shelter.ShelterRepository;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
@Transactional
public class PesananService {

    private final PesananRepository pesananRepository;
    private final PenggunaRepository penggunaRepository;
    private final HewanRepository hewanRepository;
    private final ShelterRepository shelterRepository;
    private final FormRepository formRepository;
    private final BaseResponse res;

    @Autowired
    private KirimEmailPesan kirimEmailPesan;

    @Value("${midtrans.server.key}")
    private String serverKey;

    @Autowired
    public PesananService(FormRepository formRepository, HewanRepository hewanRepository,
            PenggunaRepository penggunaRepository, PesananRepository pesananRepository, BaseResponse res,
            ShelterRepository shelterRepository) {
        this.formRepository = formRepository;
        this.hewanRepository = hewanRepository;
        this.penggunaRepository = penggunaRepository;
        this.pesananRepository = pesananRepository;
        this.res = res;
        this.shelterRepository = shelterRepository;
    }

    public Map<String, Object> createPesanan(Long idHewan, Long idPengguna) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(idPengguna)
                .ifPresentOrElse(pengguna -> {
                    hewanRepository.findById(idHewan)
                            .ifPresentOrElse(hewan -> {
                                Pesanan pesanan = new Pesanan();
                                pesanan.setPengguna(pengguna);
                                pesanan.setHewan(hewan);
                                pesanan.setShelter(hewan.getShelter());
                                pesananRepository.save(pesanan);
                                response.putAll(
                                        res.CREATED("Pesanan telah terbuat", null, null));
                            }, () -> response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null,
                                    "UNAUTHORIZED, Hewan tidak ditemukan")));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "UNAUTHORIZED, Pengguna tidak ditemukan")));
        return response;
    }

    public Map<String, Object> viewPesananPengguna(Long idPengguna) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(idPengguna)
                .ifPresentOrElse(pengguna -> {
                    try {
                        List<Pesanan> pesananBelumDibayar = pesananRepository.findByStatusPembayaran("pending");
                        System.out.println(pesananBelumDibayar);
                        updatePesanan(pesananBelumDibayar);
                        response.putAll(res.OK("Data pesanan diperlihatkan", pengguna.getPesanan(), null));
                    } catch (Exception e) {
                        response.putAll(
                                res.FORBIDDEN("Gagal memperbarui data pesanan", null, "FORBIDDEN, " + e.getMessage()));
                    }
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "UNAUTHORIZED, Pengguna tidak ditemukan")));
        return response;
    }

    public Map<String, Object> viewPesananShelter(Long idShelter) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(idShelter)
                .ifPresentOrElse(shelter -> {
                    try {
                        List<Pesanan> pesananBelumDibayar = pesananRepository
                                .findByStatusPembayaranAndShelter("pending", shelter);
                        updatePesanan(pesananBelumDibayar);
                    } catch (Exception e) {
                        response.putAll(
                                res.FORBIDDEN("Gagal memperbarui data pesanan", null, "FORBIDDEN, " + e.getMessage()));
                    }
                    Map<String, Object> data = new LinkedHashMap<>();
                    data.put("dataMasuk", pesananRepository.countByStatusIsNull());
                    data.put("dataTerima", pesananRepository.countByStatus("DITERIMA"));
                    data.put("dataTolak", pesananRepository.countByStatus("DITOLAK"));
                    data.put("dataPesanan", shelter.getPesanan());
                    response.putAll(res.OK("Data pesanan diperlihatkan", data, null));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "UNAUTHORIZED, Shelter tidak ditemukan")));
        return response;
    }

    public Map<String, Object> isiForm(FormDTO formDTO, Long id, Long idPengguna) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(idPengguna)
                .ifPresentOrElse(pengguna -> {
                    pesananRepository.findById(id)
                            .ifPresentOrElse(pesanan -> {
                                Form form = new Form(
                                        formDTO.getDaerah(),
                                        formDTO.getEmail(),
                                        formDTO.isHewanSebelumnya(),
                                        formDTO.getJalan(),
                                        formDTO.getJenisHewan(),
                                        formDTO.getJenisKelamin(),
                                        formDTO.isKeluargaAlergi(),
                                        formDTO.isLingkunganAman(),
                                        formDTO.isMemilikiHewan(),
                                        formDTO.getNama(),
                                        formDTO.getNoTelepon(),
                                        formDTO.getPekerjaanStatus(),
                                        pesanan,
                                        formDTO.getTanggalHewan(),
                                        formDTO.getTanggalLahir(),
                                        formDTO.getTempatTinggal(),
                                        formDTO.getZipCode());
                                formRepository.save(form);
                                pesanan.setForm(form);
                                pesananRepository.save(pesanan);
                                response.putAll(res.CREATED("Form telah terisi", null, null));
                            }, () -> response.putAll(res.UNAUTHORIZED("Pesanan tidak ditemukan", null,
                                    "UNAUTHORIZED, Pesanan tidak ditemukan")));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "UNAUTHORIZED, Pengguna tidak ditemukan")));
        return response;
    }

    public Map<String, Object> confirmForm(PesananDTO pesananDTO, Long id, Long idShelter) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(idShelter)
                .ifPresentOrElse(pengguna -> {
                    pesananRepository.findById(id)
                            .ifPresentOrElse(pesanan -> {
                                pesanan.setStatus(pesananDTO.getStatus());
                                if (pesananDTO.getStatus().equals("DITERIMA")) {
                                    pesanan.setStatusPembayaran("pending");
                                    Hewan hewan = pesanan.getHewan();
                                    int hargaHewan = (int) Math.round(hewan.getHarga());
                                    kirimEmailPesan.sendEmail(pengguna.getEmail(),
                                            "Pesanan anda diterima silahkan melanjutkan pembayaran",
                                            "Status Pesanan" + pesanan.getKodePemesanan());
                                    try {
                                        String tokenLinkPembayaran = createSnapToken(pesanan.getKodePemesanan(),
                                                hargaHewan);
                                        response.putAll(
                                                res.OK("Pesanan diterima dan pembayaran dibuat dengan token dan link = "
                                                        + tokenLinkPembayaran, null, null));
                                    } catch (Exception e) {
                                        response.putAll(
                                                res.FORBIDDEN("Gagal terhubung ke server pembayaran: ",
                                                        null, "FORBIDDEN, " + e.getMessage()));
                                    }
                                } else if (pesananDTO.getStatus().equals("DITOLAK")) {
                                    kirimEmailPesan.sendEmail(pengguna.getEmail(), "Maaf, pesanan anda ditolak",
                                            "Status Pesanan" + pesanan.getKodePemesanan());
                                    response.putAll(res.OK("Pesanan ditolak", null, null));
                                }
                                pesananRepository.save(pesanan);
                            }, () -> response.putAll(res.UNAUTHORIZED("Pesanan tidak ditemukan", null,
                                    "UNAUTHORIZED, Pesanan tidak ditemukan")));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "UNAUTHORIZED, Shelter tidak ditemukan")));
        return response;
    }

    public String createSnapToken(String orderId, int grossAmount) throws Exception {
        System.out.println("DEBUG SERVER KEY: [" + serverKey + "]");
        String authString = serverKey + ":";
        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());

        String jsonBody = """
                {
                  "transaction_details": {
                    "order_id": "%s",
                    "gross_amount": %d
                  },
                  "credit_card": {
                    "secure": true
                  }
                }
                """.formatted(orderId, grossAmount);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://app.sandbox.midtrans.com/snap/v1/transactions"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("authorization", "Basic " + encodedAuth)
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getTransactionStatus(String orderId) throws Exception {
        String authString = serverKey + ":";
        // Kunci ke UTF-8
        String encodedAuth = Base64.getEncoder()
                .encodeToString(authString.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        String url = "https://api.sandbox.midtrans.com/v2/" + orderId + "/status";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json") // Huruf A besar
                .header("Authorization", "Basic " + encodedAuth) // Huruf A besar
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public void updatePesanan(List<Pesanan> pesananBelumDibayar) throws Exception {
        ObjectMapper mapper = new ObjectMapper(); // Bikin objek mapper cukup di luar loop biar hemat memori

        for (Pesanan pesanan : pesananBelumDibayar) {
            String fullDataMidtrans = getTransactionStatus(pesanan.getKodePemesanan());
            System.out.println("Cek Midtrans untuk " + pesanan.getKodePemesanan() + " : " + fullDataMidtrans);

            if (fullDataMidtrans == null || fullDataMidtrans.isBlank()) {
                System.out.println("Balasan kosong! Melewati pesanan " + pesanan.getKodePemesanan());
                continue;
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> responseMap = mapper.readValue(fullDataMidtrans, Map.class);

            if (responseMap.containsKey("transaction_status")) {
                String newStatusPembayaran = (String) responseMap.get("transaction_status");
                pesanan.setStatusPembayaran(newStatusPembayaran);
                pesananRepository.save(pesanan);
            } else {
                System.out.println("Transaksi belum terdaftar di Midtrans: " + pesanan.getKodePemesanan());
            }
        }
    }
}