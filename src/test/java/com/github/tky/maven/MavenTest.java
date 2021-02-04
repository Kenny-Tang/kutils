package com.github.tky.maven;

import org.apache.maven.model.Model;
import org.junit.Test;

import com.github.tky.kutils.Mavens;
import com.test.BaseTest;

public class MavenTest extends BaseTest {

	@Test
	public void testFetchPom() {
		Model model = Mavens.getMaven();
		String groupId = model.getGroupId();
		String artifactId = model.getArtifactId();
		assertEquals("com.github.kenny-tang", groupId);
		assertEquals("kutils", artifactId);
	}
}
