/* ==================================================================
 * ControlTypeTests.java - 20/09/2016 6:00:25 AM
 * 
 * Copyright 2007-2016 SolarNetwork.net Dev Team
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
 * 02111-1307 USA
 * ==================================================================
 */

package net.solarnetwork.node.loxone.domain.test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.solarnetwork.node.loxone.domain.ControlType;

/**
 * Unit tests for the {@link ControlType} class.
 * 
 * @author matt
 * @version 1.0
 */
public class ControlTypeTests {

	private ObjectMapper objectMapper;

	@Before
	public void setup() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void jsonSerialize() throws IOException {
		ControlType[] values = ControlType.values();
		for ( ControlType type : values ) {
			String json = objectMapper.writeValueAsString(type);
			assertEquals("\"" + type.getKey() + "\"", json);
		}
	}

	@Test
	public void jsonDeserialize() throws IOException {
		ControlType[] values = ControlType.values();
		for ( ControlType type : values ) {
			ControlType parsed = objectMapper.readValue("\"" + type.getKey() + "\"", ControlType.class);
			assertEquals(type, parsed);
		}
	}

	@Test
	public void jsonDeserializeUnknownValue() throws IOException {
		ControlType parsed = objectMapper.readValue("\"what the heck\"", ControlType.class);
		assertEquals(ControlType.Unknown, parsed);
	}

	@Test
	public void forIndexValue() {
		for ( int i = 0; i < 5; i++ ) {
			ControlType type = ControlType.forIndexValue(i);
			assertEquals(i + 1, type.ordinal());
		}
	}

	@Test
	public void forKeyValueUnknown() {
		ControlType type = ControlType.forIndexValue(121203918);
		assertEquals(ControlType.Unknown, type);
	}

}
