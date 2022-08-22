package com.Ako.wiki.controller;

import com.Ako.wiki.domain.Ebook;
import com.Ako.wiki.resp.CommonResp;
import com.Ako.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController // Used when it returns a String.
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list") // we can change "/ebook/list" to "/list" since we add @RequestMapping("/ebook");
    public CommonResp list() {
        CommonResp<List<Ebook>> resp = new CommonResp<>();
        List<Ebook> list = ebookService.list();
        resp.setContent(list);
        return resp;
    }
}
