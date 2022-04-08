package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
//@ResponseBody
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

//    {
//        "pcno":0,
//        "comment":"hihihi33",
//        "commenter":"asdf"
//    }

    // 댓글을 수정하는 메서드
    @PatchMapping("/comments/{cno}")
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto) {
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setCno(cno);
        System.out.println("dto = " + dto);

        try {
            if(commentService.modify(dto) != 1)
                throw new Exception("Modify Failed");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }



//    POSTMAN에서 Headers에 Content-Type application/json 추가
//    {
//        "pcno":0,
//        "comment":"hi"
//    }

    // 댓글을 등록하는 메서드
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
//        String commenter = (String) session.getAttribute("id");
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setBno(bno);
        System.out.println("dto = " + dto);

        try {
            if(commentService.write(dto) != 1)
                throw new Exception("Write Failed");

            return new ResponseEntity<>("WRITE_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRITE_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 지정된 댓글을 삭제하는 메서드
    @DeleteMapping("/comments/{cno}")   // http://localhost/ch4/comments/18?bno=524 <-- 삭제할 댓글 번호
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session){
//        String commenter = (String) session.getAttribute("id");
        String commenter = "asdf";
        try {
            int rowCnt = commentService.remove(cno, bno, commenter);

            if(rowCnt != 1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 지정된 게시물의 모든 댓글을 가져오는 메서드
    @GetMapping("/comments")    // comments?bno=1080 GET
    public ResponseEntity<List<CommentDto>> list(Integer bno){
        System.out.println("bno = " + bno);
        List<CommentDto> list = null;

        try {
            list = commentService.getList(bno);
            System.out.println("list = " + list);
            return new ResponseEntity<List<CommentDto>>(list,HttpStatus.OK);    // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST);    // 400
        }

    }
}
