package com.unifs.sdbst.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "file")
public class FileController {

//    @ApiOperation(value="获取图片")
//    @ResponseBody
//    @RequestMapping(value="imgs",produces = MediaType.IMAGE_JPEG_VALUE)
//    public BufferedImage  getImg(String url, HttpServletRequest request) throws IOException {
//
//        File filePath = new File(ResourceUtils.getURL("classpath:").getPath());
//
//        System.out.println(filePath);
//        String rootPath = filePath.getParentFile().getParentFile().getParent()+File.separator+"sdbstImages"+File.separator;
//        System.out.println(rootPath);
//        System.out.println(rootPath+url);
//
//
//        return ImageIO.read(new FileInputStream(new File(rootPath+url)));
////        return "";
//    }

//    public static final String imgPath = "/home/";
//
//    @ApiOperation(value="獲取圖片", notes="根據URL獲取")
//    @RequestMapping(value = "/img",produces="application/json;charset=UTF-8",method = RequestMethod.GET)
//    @ResponseBody
//    public void  getImage(HttpServletRequest request, HttpServletResponse response,String url,String type) throws IOException {
//        if (StringUtils.isEmpty(url)) {
//            return ;
//        }
//        if(StringUtils.isEmpty(type)){
//            url += "@3x.png";
//            System.out.println(url);
//
//        }else {
//            url = url+"@"+type+".png";
//            System.out.println(url);
//
//        }
//        System.out.println(url);
//        File filePath = new File(ResourceUtils.getURL("classpath:").getPath());
////      /home/tomcat9/webapps/sdbst/WEB-INF/classes
//        System.out.println(filePath);
//        String rootPath = filePath.getParentFile().getParentFile().getParent()+ File.separator+"sdbstUpload"+File.separator;
//        System.out.println(rootPath);
////      /home/tomcat9/webapps/sdbstUpload
//
//        //    /home/sdbstUpload
//        String rootPath1 = filePath.getParentFile().getParentFile().getParentFile().getParentFile().getParent()+ File.separator+"sdbstUpload"+File.separator;
//        System.out.println(rootPath1);
//
//        String path = rootPath.substring(rootPath.indexOf("sdbstUpload"));
//        System.out.println(path);
//        System.out.println("19.202.137.134:8090/"+path);
//        //读取本地图片输入流
//        try {
//            FileInputStream inputStream = new FileInputStream(rootPath+url);
//            int i = inputStream.available();
//            //byte数组用于存放图片字节数据
//            byte[] buff = new byte[i];
//            inputStream.read(buff);
//            //记得关闭输入流
//            inputStream.close();
//            //设置发送到客户端的响应内容类型
//            //response.setContentType("image/*"); //图片下载
//            response.setHeader("Content-type", "image/png");//图片展示
//            OutputStream out = response.getOutputStream();
//            out.write(buff);
//            //关闭响应输出流
//            out.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("找不到图片:"+imgPath+url);
//        }
//    }


}
