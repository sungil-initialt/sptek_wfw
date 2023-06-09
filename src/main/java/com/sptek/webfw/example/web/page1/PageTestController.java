package com.sptek.webfw.example.web.page1;

import com.sptek.webfw.code.ApiErrorCode;
import com.sptek.webfw.example.web.page1.dto.TbTest;
import com.sptek.webfw.exception.ApiBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class PageTestController {

    @Autowired
    private PageTestService pageTestService;
    private final String PAGE_PATH = "pages/example/page1/";

    //기본 테스트
    @RequestMapping("/welcome")
    public String welcome(Model model) {
        log.debug("called welcome");

        model.addAttribute("message", "welcome");
        return PAGE_PATH + "welcome";
    }

    //비즈니스 에러 페이지 테스트
    @RequestMapping("/businessErr")
    public String businessErr(Model model) {
        log.debug("called businessErr");

        if(1==1) throw new ApiBusinessException(ApiErrorCode.BUSINESS_DEFAULT_ERROR, "businessErr");
        return "xxx";
    }

    //Mybatis 테스트
    @RequestMapping("/dbConnectTest")
    public String dbConnectTest(Model model) {
        log.debug("called dbConnectTest");

        int result = pageTestService.return1();
        model.addAttribute("result", result);
        return PAGE_PATH + "simpleResultPage";
    }

    @RequestMapping("/replicationMasterTest")
    public String replicationMasterTest(Model model) {
        log.debug("called replicationMasterTest");

        int result = pageTestService.replicationMasterTest();
        model.addAttribute("result", result);
        return PAGE_PATH + "simpleResultPage";
    }

    @RequestMapping("/replicationSlaveTest")
    public String replicationSlaveTest(Model model) {
        log.debug("called replicationSlaveTest");

        int result = pageTestService.replicationSlaveTest();
        model.addAttribute("result", result);
        return PAGE_PATH + "simpleResultPage";
    }

    @RequestMapping("/selectOneTest")
    public String selectOneTest(Model model) {
        log.debug("called selectOneTest");

        TbTest tbTest = pageTestService.selectOneTest();
        model.addAttribute("result", tbTest.toString());
        return PAGE_PATH + "simpleResultPage";
    }

    @RequestMapping("/selectListTest")
    public String selectListTest(Model model) {
        log.debug("called selectListTest");

        List<TbTest> tbTests = pageTestService.selectListTest();
        model.addAttribute("result", tbTests.toString());
        return PAGE_PATH + "simpleResultPage";
    }

    @RequestMapping("/selectMapTest")
    public String queryForMapTest(Model model) {
        log.debug("called queryForMapTest");

        Map<?, ?> resultMap = pageTestService.selectMapTest();
        model.addAttribute("result", resultMap.toString());
        return PAGE_PATH + "simpleResultPage";
    }

    @RequestMapping("/insertTest")
    public String insertTest(Model model) {
        log.debug("called insertTest");
        TbTest tbTest = TbTest.builder()
                .c1(41)
                .c2(42)
                .c3(43).build();

        int result = pageTestService.insertTest(tbTest);
        model.addAttribute("result", result);
        return PAGE_PATH + "simpleResultPage";
    }

    @RequestMapping("/updateTest")
    public String updateTest(Model model) {
        log.debug("called updateTest");
        TbTest tbTest = TbTest.builder()
                .c1(41)
                .c2(422)
                .c3(433).build();

        int result = pageTestService.updateTest(tbTest);
        model.addAttribute("result", result);
        return PAGE_PATH + "simpleResultPage";
    }

    @RequestMapping("/deleteTest")
    public String deleteTest(Model model) {
        log.debug("called deleteTest");
        TbTest tbTest = TbTest.builder()
                .c1(41).build();

        int result = pageTestService.deleteTest(tbTest);
        model.addAttribute("result", result);
        return PAGE_PATH + "simpleResultPage";
    }
}
