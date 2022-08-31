package com.github.kpodvolecky.less.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilsTest {
    @Test void getDestinationTest(){
        assertEquals("/tmp/dist/client/theme.css", Utils.getDestinationPath("/tmp/less", "/tmp/less/client/theme.less", "/tmp/dist"));
        assertEquals("/tmp/dist/client/theme.css", Utils.getDestinationPath("/tmp/less/", "/tmp/less/client/theme.less", "/tmp/dist"));
        assertEquals("/tmp/dist/client/theme.css", Utils.getDestinationPath("/tmp/less", "/tmp/less/client/theme.less", "/tmp/dist/"));
        assertEquals("/tmp/dist/client/theme.css", Utils.getDestinationPath("/tmp/less/", "/tmp/less/client/theme.less", "/tmp/dist/"));
        assertEquals("d:/tmp/dist/client/theme.css", Utils.getDestinationPath("c:/tmp/less", "c:/tmp/less/client/theme.less", "d:/tmp/dist"));
        assertEquals("d:/tmp/dist/client/theme.css", Utils.getDestinationPath("c:/tmp/less/", "c:/tmp/less/client/theme.less", "d:/tmp/dist/"));
    }
}
