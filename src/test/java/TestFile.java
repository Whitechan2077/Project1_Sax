import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.AccountDTO;
import com.sax.services.IAccountService;
import com.sax.services.ICtkmSachService;
import com.sax.services.ICtkmService;
import com.sax.services.impl.AccountService;
import com.sax.services.impl.CtkmSachService;
import com.sax.services.impl.CtkmService;
import com.sax.utils.AccountUtils;
import com.sax.utils.ContextUtils;
import com.sax.utils.ImageUtils;
import org.junit.Test;

import java.util.List;
import java.util.Set;

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
    }
}
