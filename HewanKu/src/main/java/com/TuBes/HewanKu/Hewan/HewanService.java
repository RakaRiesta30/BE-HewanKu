package com.TuBes.HewanKu.Hewan;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TuBes.HewanKu.BaseResponse;
import com.TuBes.HewanKu.FileStorageService;
import com.TuBes.HewanKu.Pengguna.PenggunaRepository;
import com.TuBes.HewanKu.Pesanan.Pesanan;
import com.TuBes.HewanKu.Pesanan.PesananRepository;
import com.TuBes.HewanKu.Pesanan.PesananService;
import com.TuBes.HewanKu.Shelter.ShelterRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HewanService {
    private final HewanRepository hewanRepository;
    private final ShelterRepository shelterRepository;
    private final PenggunaRepository penggunaRepository;
    private final BaseResponse res;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PesananService pesananService;

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    public HewanService(HewanRepository hewanRepository, PenggunaRepository penggunaRepository, BaseResponse res,
            ShelterRepository shelterRepository) {
        this.hewanRepository = hewanRepository;
        this.penggunaRepository = penggunaRepository;
        this.res = res;
        this.shelterRepository = shelterRepository;
    }

    public Map<String, Object> createHewan(HewanDTO hewanDTO, Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    MultipartFile foto = hewanDTO.getFoto();
                    try {
                        String urlImage = fileStorageService.uploadFile(foto);
                        Hewan hewan = new Hewan(
                                hewanDTO.getHarga(),
                                hewanDTO.getJenis(),
                                hewanDTO.getNama(),
                                0.0,
                                shelter,
                                hewanDTO.getStatus(),
                                LocalDate.now(),
                                hewanDTO.getUmur(),
                                hewanDTO.getJenisKelamin(),
                                hewanDTO.getNomorTelepon(),
                                urlImage);
                        shelter.getDaftarHewan().add(hewan);
                        hewanRepository.save(hewan);
                        shelterRepository.save(shelter);
                        response.putAll(res.CREATED("Hewan telah terdaftar", null, null));
                    } catch (IOException e) {
                        response.putAll(res.CREATED("Hewan gagal terdaftar", e.getMessage(), null));
                    }
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "Unauthorized, Shelter tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> viewHewanShelter(Long id) {
        Map<String, Object> response = new LinkedHashMap<>();

        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    List<Hewan> daftarHewan = shelter.getDaftarHewan();
                    long totalHewan = hewanRepository.countByShelter_Id(id);
                    Map<String, Object> data = new LinkedHashMap<>();
                    data.put("totalHewan", totalHewan);
                    data.put("dataHewan", daftarHewan);
                    response.putAll(res.OK("Hewan ditemukan", data, null));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "Unauthorized, Shelter tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> viewHewanPengguna(Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    List<Pesanan> pesananBelumDibayar = pesananRepository.findByStatusPembayaran("pending");
                    System.out.println(pesananBelumDibayar);
                    try {
                        pesananService.updatePesanan(pesananBelumDibayar);
                    } catch (Exception e) {
                        res.UNAUTHORIZED("ERROR", e, "ERROR, " + e);
                    }
                    List<Hewan> daftarRating = hewanRepository.findAllByOrderByRating();
                    List<Hewan> hewanUnggulan = shelterRepository.findHewanOrderByShelter();
                    List<Hewan> hewanRandom = hewanRepository.findAll();
                    List<Hewan> daftarFavorit = hewanRepository.findAllByOrderByJumlahFavorit();
                    Collections.shuffle(hewanRandom);
                    Map<String, Object> daftarHewan = Map.of("hewanUnggulan", hewanUnggulan, "rekomendasiUntukmu",
                            hewanRandom, "ratingTertinggi", daftarRating, "daftarFavorit", daftarFavorit);
                    response.putAll(res.OK("Shelter ditemukan", daftarHewan, null));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "Unauthorized, Pengguna tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> editHewan(HewanDTO hewanDTO, Long id, Long idHewan) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    hewanRepository.findById(idHewan)
                            .ifPresentOrElse(hewan -> {
                                hewan.setHarga(hewanDTO.getHarga());
                                hewan.setJenis(hewanDTO.getJenis());
                                hewan.setNama(hewanDTO.getNama());
                                hewan.setStatus(hewanDTO.getStatus());
                                hewan.setUpdatedDate(LocalDate.now());
                                hewan.setShelter(shelter);
                                hewan.setUmur(hewanDTO.getUmur());
                                hewan.setJenisKelamin(hewanDTO.getJenisKelamin());
                                hewanRepository.save(hewan);
                                response.putAll(res.OK("Hewan telah diubah", null, null));
                            }, () -> {
                                response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null,
                                        "Unauthorized, Hewan tidak ditemukan "));
                            });
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "Unauthorized, Shelter tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> deleteHewan(Long id, Long idHewan) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    hewanRepository.findById(idHewan)
                            .ifPresentOrElse(hewan -> {
                                hewanRepository.delete(hewan);
                                response.putAll(res.OK("Hewan telah dihapus", null, null));
                            }, () -> {
                                response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null,
                                        "Unauthorized, Hewan tidak ditemukan "));
                            });
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "Unauthorized, Shelter tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> filterHewan(Long id, FilterDTO filterDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    List<Hewan> hewanList = hewanRepository.getReferenceByJenisAndHargaBetween(filterDTO.getJenis(),
                            filterDTO.getHargaMin(), filterDTO.getHargaMax());
                    response.putAll(res.OK("Hewan telah difilter", hewanList, null));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "Unauthorized, Pengguna tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> detailHewan(Long id, Long idHewan) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    hewanRepository.findById(idHewan)
                            .ifPresentOrElse(hewan -> {
                                response.putAll(res.OK("Hewan ditemukan", hewan, null));
                            }, () -> response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null,
                                    "Unauthorized, Hewan tidak ditemukan")));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "Unauthorized, Pengguna tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> tambahFavorit(Long id, Long idHewan) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    hewanRepository.findById(idHewan)
                            .ifPresentOrElse(hewan -> {
                                List<Hewan> favList = pengguna.getFavorit();
                                if (!favList.contains(hewan)) {
                                    favList.add(hewan);
                                    pengguna.setFavorit(favList);
                                    hewan.setJumlahFavorit(hewan.getJumlahFavorit() + 1);
                                    hewanRepository.save(hewan);
                                    penggunaRepository.save(pengguna);
                                    response.putAll(res.OK("Hewan ditambahkan menjadi favorit", null, null));
                                } else {
                                    response.putAll(res.FORBIDDEN("Hewan sudah ditambahkan ke favorit", null, null));
                                }
                            }, () -> response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null,
                                    "Unauthorized, Hewan tidak ditemukan")));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "Unauthorized, Pengguna tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> hapusFavorit(Long id, Long idHewan) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    hewanRepository.findById(idHewan)
                            .ifPresentOrElse(hewan -> {
                                List<Hewan> favList = pengguna.getFavorit();
                                if (favList.contains(hewan)) {
                                    favList.remove(hewan);
                                    pengguna.setFavorit(favList);
                                    hewan.setJumlahFavorit(hewan.getJumlahFavorit() - 1);
                                    hewanRepository.save(hewan);
                                    penggunaRepository.save(pengguna);
                                    response.putAll(res.OK("Hewan dihapus dari favorit", null, null));
                                } else {
                                    response.putAll(res.FORBIDDEN("Hewan tidak menjadi favorit", null, null));
                                }
                            }, () -> response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null,
                                    "Unauthorized, Hewan tidak ditemukan")));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "Unauthorized, Pengguna tidak ditemukan ")));
        return response;
    }
}
