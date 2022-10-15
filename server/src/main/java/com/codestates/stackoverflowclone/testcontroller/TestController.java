package com.codestates.stackoverflowclone.testcontroller;

import com.codestates.stackoverflowclone.tag.entity.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://s3-clone-sof.s3-website.ap-northeast-2.amazonaws.com", allowCredentials = "true")
public class TestController {

    @GetMapping("/testcall")
    public ResponseEntity getTest() {
        Map<String, String> test = new HashMap<>();
        test.put("test1", "result");
        test.put("test2", "result");
        return new ResponseEntity(test, HttpStatus.OK);
    }

    @GetMapping("/testcall/getsession")
    public ResponseEntity getSessionInfo(HttpServletRequest request) {
        System.out.println("여기는 들어오나 1 : ");
        HttpSession session = request.getSession(false);
        System.out.println("여기는 들어오나 2 : ");
        Enumeration<String> attrName = session.getAttributeNames();
        System.out.println("여기는 들어오나 3 : ");
        System.out.println("arrtName에 뭔가 있나 : "+attrName);
        while (attrName.hasMoreElements()) {
            String attr = attrName.nextElement();
            System.out.println("뭔가 출력되는게 있나요? "+session.getAttribute(attr));
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/testcall/ask")
    public ResponseEntity postTestList(@RequestBody TestPostDto testPostDto) {

        if (testPostDto.getTitle() == null) {
            System.out.println("제목이 널 입니다.");
        } else {
            System.out.println("제목이 널이 아닙니다. " + testPostDto.getTitle());
        }

        if (testPostDto.getTagList() == null) {
            System.out.println("태그 리스트가 널 입니다.");
        } else {
            System.out.println("태그 리스트가 널이 아닙니다. " + testPostDto.getTagList().size());
        }
        List<TagPostDto> tagList = testPostDto.getTagList();
        for (int i = 0; i < tagList.size(); i++) {
            System.out.println(tagList.get(i).getTagName());
        }


//        List<Tag> tagList = testPostDto.getTagList();
//        for (int i = 0; i < tagList.size(); i++) {
//            Tag tag = new Tag();
//            tag.setTagName(tagList.get(i).getTagName());
//        }
//
//        for (int i = 0; i < tagList.size(); i++) {
//            tagList.get(i).getTagName();
//        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
