package com.TuBes.HewanKu.Pesanan;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.BaseResponse;
import com.TuBes.HewanKu.Hewan.HewanRepository;
import com.TuBes.HewanKu.Pengguna.PenggunaRepository;
import com.TuBes.HewanKu.Shelter.ShelterRepository;

import jakarta.transaction.Transactional;

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
                                response.putAll(res.CREATED("Pesanan telah terbuat", null, null));
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
                    response.putAll(res.OK("Data pesanan diperlihatkan", pengguna.getPesanan(), null));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "UNAUTHORIZED, Pengguna tidak ditemukan")));
        return response;
    }

    public Map<String, Object> viewPesananShelter(Long idShelter) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(idShelter)
                .ifPresentOrElse(shelter -> {
                    Map<String, Object> data = new LinkedHashMap<>();
                    data.put("dataMasuk", pesananRepository.countByStatusIsNull());
                    data.put("dataTerima", pesananRepository.countByStatus("TERIMA"));
                    data.put("dataTolak", pesananRepository.countByStatus("TOLAK"));
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
                                pesananRepository.save(pesanan);
                                response.putAll(res.CREATED("Form telah terisi", null, null));
                            }, () -> response.putAll(res.UNAUTHORIZED("Pesanan tidak ditemukan", null,
                                    "UNAUTHORIZED, Pesanan tidak ditemukan")));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "UNAUTHORIZED, Shelter tidak ditemukan")));
        return response;
    }
}
