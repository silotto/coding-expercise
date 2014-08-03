package com.ufukuzun.kodility.dao.challenge;

import com.ufukuzun.kodility.AbstractDaoTest;
import com.ufukuzun.kodility.domain.challenge.Level;
import com.ufukuzun.kodility.testutils.builder.LevelBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.ufukuzun.kodility.testutils.asserts.Asserts.assertOrderedItems;

public class LevelDaoTest extends AbstractDaoTest {

    @Autowired
    private LevelDao levelDao;

    @Test
    public void shouldFindAllOrdered() {
        Level level1 = new LevelBuilder().priority(3).persist(getCurrentSession());
        Level level2 = new LevelBuilder().priority(1).persist(getCurrentSession());
        Level level3 = new LevelBuilder().priority(2).persist(getCurrentSession());

        flushAndClear();

        List<Level> orderedLevels = levelDao.findAllOrdered();

        assertOrderedItems(orderedLevels, level2, level3, level1);
    }

}