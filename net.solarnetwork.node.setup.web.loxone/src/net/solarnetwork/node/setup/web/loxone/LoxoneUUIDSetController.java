/* ==================================================================
 * LoxoneUUIDSetController.java - 27/09/2016 5:44:17 PM
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

package net.solarnetwork.node.setup.web.loxone;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import net.solarnetwork.domain.SortDescriptor;
import net.solarnetwork.node.loxone.LoxoneService;
import net.solarnetwork.node.loxone.domain.DatumUUIDEntity;
import net.solarnetwork.node.loxone.domain.UUIDEntityParameters;
import net.solarnetwork.node.loxone.domain.UUIDSetEntity;
import net.solarnetwork.web.domain.Response;

/**
 * Controller for managing UUID sets.
 * 
 * @author matt
 * @version 1.0
 */
@RestController
@RequestMapping("/a/loxone/{configId}/uuidsets")
public class LoxoneUUIDSetController extends BaseLoxoneWebServiceController {

	/**
	 * Get all available datum UUID set values.
	 * 
	 * @param configId
	 *        The config ID to get the datum UUIDs for.
	 * @return All available UUIDs.
	 */
	@RequestMapping(value = "/datum", method = RequestMethod.GET)
	public Response<Collection<UUID>> getDatumUUIDSet(@PathVariable("configId") String configId) {
		return getUUIDSetForConfigId(configId, DatumUUIDEntity.class, null);
	}

	/**
	 * Add or remove datum UUID set values.
	 * 
	 * @param configId
	 *        The config ID to add or remove UUIDs to or from.
	 * @param patchSet
	 * @return
	 */
	@RequestMapping(value = "/datum", method = RequestMethod.PATCH)
	public Response<Object> updateDatumUUIDSet(@PathVariable("configId") String configId,
			@RequestBody DatumUUIDPatchSet patchSet) {
		return updateUUIDSetForConfigId(configId, DatumUUIDEntity.class, patchSet.getAdd(),
				patchSet.getRemove(), patchSet.getParameters());
	}

	private <T extends UUIDSetEntity<P>, P extends UUIDEntityParameters> Response<Collection<UUID>> getUUIDSetForConfigId(
			String configId, Class<T> type, List<SortDescriptor> sortDescriptors) {
		LoxoneService service = serviceForConfigId(configId);
		if ( service == null ) {
			return new Response<Collection<UUID>>(Boolean.FALSE, "404", "Service not available", null);
		}
		Collection<UUID> result = service.getUUIDSet(type, sortDescriptors);
		return Response.response(result);
	}

	private <T extends UUIDSetEntity<P>, P extends UUIDEntityParameters> Response<Object> updateUUIDSetForConfigId(
			String configId, Class<T> type, Collection<UUID> add, Collection<UUID> remove,
			Map<UUID, P> parameters) {
		LoxoneService service = serviceForConfigId(configId);
		if ( service == null ) {
			return new Response<Object>(Boolean.FALSE, "404", "Service not available", null);
		}
		service.updateUUIDSet(type, add, remove, parameters);
		return Response.response(null);
	}

}
