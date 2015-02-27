package bikeshareinterfaces;

import java.util.List;

import resources.LocationEntity;

public interface LocationEntityInterface {
	
	public LocationEntity getLocation(int location_id);
	public void addLocation(LocationEntity location);
	public List<LocationEntity> getLocations();
	
}
