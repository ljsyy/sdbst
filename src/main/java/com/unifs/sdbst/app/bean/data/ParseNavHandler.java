package com.unifs.sdbst.app.bean.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @title: ParseNavHandler
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/10/28 15:31
 */
public class ParseNavHandler
        extends DefaultHandler {
    private ShundeNavEntity shundeNav = null;
    private List<ChildNavEntity> childList = null;
    private ChildNavEntity childNav = null;
    private boolean readChild = false;
    private boolean hasChildTarget = false;
    private String tagName = null;

    public ParseNavHandler(ShundeNavEntity shundeNav) {
        this.shundeNav = shundeNav;
    }

    public ShundeNavEntity getShundeNav() {
        return this.shundeNav;
    }

    public void setShundeNav(ShundeNavEntity shundeNav) {
        this.shundeNav = shundeNav;
    }

    public void characters(char[] ch, int start, int length) {
        String temp = new String(ch, start, length);
        if (this.tagName.equals("id")) {
            if (this.readChild) {
                this.childNav.setId(temp);
            } else {
                this.shundeNav.setId(temp);
            }
        } else if (this.tagName.equals("name")) {
            if (this.readChild) {
                this.childNav.setName(temp);
            } else {
                this.shundeNav.setName(temp);
            }
        } else if (this.tagName.equals("hasChildren")) {
            this.childNav.setHasChildren(temp.equals("true"));
        } else if (this.tagName.equals("url")) {
            this.childNav.setUrl(temp);
        }
    }

    public void startDocument() throws SAXException {
        super.startDocument();
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        this.tagName = qName;
        if (this.tagName.equals("shundeNav")) {
            this.shundeNav = new ShundeNavEntity();
            this.readChild = false;
        } else if (this.tagName.equals("childNavList")) {
            this.childList = new ArrayList();
            this.readChild = true;
            this.hasChildTarget = true;
        } else if (this.tagName.equals("item")) {
            if ((!this.hasChildTarget) && (!this.readChild)) {
                this.childList = new ArrayList();
                this.readChild = true;
            }
            this.childNav = new ChildNavEntity();
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("item")) {
            this.childList.add(this.childNav);
        } else if (qName.equals("childNavList")) {
            this.shundeNav.setChildNavList(this.childList);
            this.readChild = false;
        } else if (qName.equals("shundeNav")) {
            if (!this.hasChildTarget) {
                this.shundeNav.setChildNavList(this.childList);
            }
            this.readChild = false;
        }
        this.tagName = "";
    }

    public void endDocument() {
    }
}