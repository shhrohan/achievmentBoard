package com.assignment.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.assignment.Model;
import com.assignment.datastructures.Achievement;
import com.assignment.datastructures.Statistic;

public class TestRunnerClass {

	@Test
	public void test_config() {

		Boolean result = Model.init();

		assertEquals(result, true);

	}

	@Test
	public void test_insert() {

		Model.init();

		Statistic stat = new Statistic();
		stat.setName("test");
		stat.setValue(1001l);
		stat.setWeight(23d);

		Long id = Statistic.insert(stat);
		stat.setId(id);
		stat.setValue(stat.getValue() + 14);


		Statistic.update(stat);

		List<Long> ids = new ArrayList<>();
		ids.add(id);
		
		System.out.println(Statistic.select(ids));
		
		Achievement achievement = new Achievement();
		achievement.setName("GOD");
		
		List<Statistic> criteria =  new ArrayList<>();
		criteria.add(stat);
		achievement.setCriteria(criteria);
		
		id = Achievement.insert(achievement);
		achievement.setId(id);
		ids = new ArrayList<>();
		ids.add(id);
		
		System.out.println(Achievement.select(ids));
		
		
		

	}

	@Test
	public void test_select() {

		assertEquals(true, true);

	}

}
