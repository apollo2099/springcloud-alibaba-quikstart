//package com.ibase.mall.auth.controller;
//
//import com.ibase.mall.auth.mq.produce.SenderService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//public class SendMsgController {
//
//    @Resource
//    private SenderService senderService;
//
//    @GetMapping(value = "/send/{msg}")
//    public void send(@PathVariable String msg) throws Exception {
//        for (int i=0;i<100000;i++){
//            senderService.send(msg+i);
//        }
//        System.out.println("send success");
//    }
//
//    @GetMapping(value = "/sendWithTags/{msg}")
//    public void sendWithTags(@PathVariable String msg) throws Exception {
//        String tag ="tagStr";
//        senderService.sendWithTags(msg,tag);
//    }
//
//
//    @GetMapping(value = "/sendTransactionalMsg/{msg}/{num}")
//    public void sendTransactionalMsg(@PathVariable String msg,@PathVariable Integer num) throws Exception {
//        senderService.sendTransactionalMsg(msg,num);
//    }
//}
