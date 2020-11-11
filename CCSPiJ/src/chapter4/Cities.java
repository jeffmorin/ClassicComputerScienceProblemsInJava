package chapter4;

import java.util.List;

public interface Cities {

	String SEATTLE = "Seattle";
	String SAN_FRANCISCO = "San Francisco";
	String LOS_ANGELES = "Los Angeles";
	String RIVERSIDE = "Riverside";
	String PHOENIX = "Phoenix";
	String CHICAGO = "Chicago";
	String BOSTON = "Boston";
	String NEW_YORK = "New York";
	String ATLANTA = "Atlanta";
	String MIAMI = "Miami";
	String DALLAS = "Dallas";
	String HOUSTON = "Houston";
	String DETROIT = "Detroit";
	String PHILADELPHIA = "Philadelphia";
	String WASHINGTON = "Washington";

	List<String> CITIES = List.of(
		SEATTLE, SAN_FRANCISCO, LOS_ANGELES, RIVERSIDE, PHOENIX,
		CHICAGO, BOSTON, NEW_YORK, ATLANTA, MIAMI,
		DALLAS, HOUSTON, DETROIT, PHILADELPHIA, WASHINGTON
	);

	class Route {
		public final String city1;
		public final String city2;
		public final float distance;

		public Route(String city1, String city2, float distance) {
			this.city1 = city1;
			this.city2 = city2;
			this.distance = distance;
		}
	}

	List<Route> ROUTES = List.of(
		new Route(SEATTLE, CHICAGO, 1737),
		new Route(SEATTLE, SAN_FRANCISCO, 678),
		new Route(SAN_FRANCISCO, RIVERSIDE, 386),
		new Route(SAN_FRANCISCO, LOS_ANGELES, 348),
		new Route(LOS_ANGELES, RIVERSIDE, 50),
		new Route(LOS_ANGELES, PHOENIX, 357),
		new Route(RIVERSIDE, PHOENIX, 307),
		new Route(RIVERSIDE, CHICAGO, 1704),
		new Route(PHOENIX, DALLAS, 887),
		new Route(PHOENIX, HOUSTON, 1015),
		new Route(DALLAS, CHICAGO, 805),
		new Route(DALLAS, ATLANTA, 721),
		new Route(DALLAS, HOUSTON, 225),
		new Route(HOUSTON, ATLANTA, 702),
		new Route(HOUSTON, MIAMI, 968),
		new Route(ATLANTA, CHICAGO, 588),
		new Route(ATLANTA, WASHINGTON, 543),
		new Route(ATLANTA, MIAMI, 604),
		new Route(MIAMI, WASHINGTON, 923),
		new Route(CHICAGO, DETROIT, 238),
		new Route(DETROIT, BOSTON, 613),
		new Route(DETROIT, WASHINGTON, 396),
		new Route(DETROIT, NEW_YORK, 482),
		new Route(BOSTON, NEW_YORK, 190),
		new Route(NEW_YORK, PHILADELPHIA, 81),
		new Route(PHILADELPHIA, WASHINGTON, 123)
	);
}
