package com.techelevator.dao;

import com.techelevator.exception.NoRecordException;
import com.techelevator.model.Style;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcStyleDaoTests extends BaseDaoTests {
    private final Style STYLE_1 = new Style(1, "style1");
    private final Style STYLE_2 = new Style(2, "style2");
    private final Style STYLE_3 = new Style(3, "style3");
    private JdbcStyleDao dao;

    @Before
    public void setup() {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        dao = new JdbcStyleDao(template);
    }

    @Test
    public void getStyleByIdReturnsCorrectStyle() {
        Style style = dao.getStyleById(STYLE_1.getStyleId());
        assertStylesMatch(STYLE_1, style);
    }

    @Test
    public void getStyleByIdThrowsNoRecordException() {
        try {
            dao.getStyleById(100);
            Assert.fail("Passing a non existent id to getStyleById should throw an exception");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("Passing a non existent id to getStyleById should throw NoRecordException, but throws a different exception");
        }
    }

    @Test
    public void getStyleByNameShouldReturnCorrectEntry() {
        Style style = dao.getStyleByName(STYLE_2.getStyleName());
        assertStylesMatch(STYLE_2, style);
    }

    @Test
    public void getStyleByNameThrowsCorrectException() {
        try {
            dao.getStyleByName("nonexistent style");
            Assert.fail("Passing a non existent name to getStyleByName should throw an exception");
        } catch (NoRecordException e) {
            return;
        } catch (Exception e) {
            Assert.fail("Passing a non existent id to getStyleById throws wrong exception type, should be NoRecordException");
        }
    }

    public void assertStylesMatch(Style expected, Style actual) {
        Assert.assertEquals(expected.getStyleId(), actual.getStyleId());
        Assert.assertEquals(expected.getStyleName(), actual.getStyleName());
    }
}
