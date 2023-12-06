import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.AccountDTO;
import com.sax.dtos.DanhMucDTO;
import com.sax.dtos.SachDTO;
import com.sax.entities.DanhMuc;
import com.sax.entities.Sach;
import com.sax.repositories.IDanhMucRepository;
import com.sax.repositories.IKhachHangRepository;
import com.sax.repositories.ISachRepository;
import com.sax.services.*;
import com.sax.services.impl.AccountService;
import com.sax.services.impl.CtkmSachService;
import com.sax.services.impl.CtkmService;
import com.sax.services.impl.SachService;
import com.sax.utils.AccountUtils;
import com.sax.utils.ContextUtils;
import com.sax.utils.ImageUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestFile {

    @Test
    public void name() throws SQLServerException {
        ICtkmSachService service = ContextUtils.getBean(CtkmSachService.class);
        ICtkmService ctkmService = ContextUtils.getBean(CtkmService.class);
//        ctkmService.searchByKeyword("1").forEach(System.out::println);
//        service.searchAllSachInCtkm("đổ",ctkmService.getById(2027)).forEach(System.out::println);
//        Set<Integer> id = Set.of(3035,3036);
//        service.deleteAll(id);
        service.searchByKeyword("nh").forEach(System.out::println);
    }

    @Test
    public void reg() {
        IAccountService service = ContextUtils.getBean(AccountService.class);
        service.searchByKeyword("1").forEach(System.out::println);
    }

    @Test
    public void s() {
        IKhachHangRepository repository = ContextUtils.getBean(IKhachHangRepository.class);
        System.out.println(repository.findRelative(3018).getId());
    }

    @Test
    public void sach() {
        ISachRepository repository = ContextUtils.getBean(ISachRepository.class);
        List<Sach> sachList = repository.findAll()
                .stream()
                .peek(sach -> {
                    List<DanhMuc> newDanhMucList = sach.getSetDanhMuc()
                            .stream()
                            .filter(danhMucDTO -> danhMucDTO.getId() != 11)
                            .toList();

                    sach.setSetDanhMuc(new HashSet<>(newDanhMucList));
                })
                .toList();
        sachList.forEach(sach -> {
            sach.getSetDanhMuc().forEach(danhMuc -> {
                System.out.println(danhMuc.getTenDanhMuc());
            });
        });
    }

    @Test
    public void danhMuc() {
        IDanhMucRepository danhMucRepository = ContextUtils.getBean(IDanhMucRepository.class);
        danhMucRepository.findAll().forEach(System.out::println);
    }
}
