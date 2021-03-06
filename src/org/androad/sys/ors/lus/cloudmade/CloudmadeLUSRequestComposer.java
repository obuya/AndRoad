package org.androad.sys.ors.lus.cloudmade;

import android.net.Uri;

import junit.framework.Assert;

import org.osmdroid.util.GeoPoint;

import org.androad.sys.ors.adt.lus.Country;
import org.androad.sys.ors.adt.lus.ICountrySubdivision;
import org.androad.sys.ors.util.constants.ORSXMLConstants;

public class CloudmadeLUSRequestComposer implements ORSXMLConstants {

	public static String reverseGeocode(final GeoPoint aGeoPoint){
		Assert.assertNotNull(aGeoPoint);

		final StringBuilder sb = new StringBuilder();
        sb.append("around=").append(aGeoPoint.getLatitudeE6() / 1E6).append(",").append(aGeoPoint.getLongitudeE6() / 1E6);
        sb.append("&distance=50");
        sb.append("&object_type=address");

		return sb.toString();
    }

	public static String createStreetaddressCityRequest(final Country nat, final ICountrySubdivision pCountrySubdivision, final String pCity, String pStreetName, final String pStreetNumber){
		if(pStreetName == null) {
			pStreetName = ""; // empty streetname is like searching for the town only.
		}

		Assert.assertNotNull(nat);
		Assert.assertTrue(pStreetNumber == null || (pStreetNumber != null && pStreetNumber.length() > 0)); // null or at least 1 char

		final StringBuilder sb = new StringBuilder();

        sb.append("query=");

		if(pStreetNumber != null){
            sb.append(Uri.encode(pStreetNumber)).append("%2C");
		}
		sb.append(Uri.encode(pStreetName));

		if(pCountrySubdivision != null){
			sb.append("%2C").append(pCountrySubdivision.getAbbreviation());
		}

		sb.append("%2C").append(Uri.encode(pCity));
		if(nat != null) {
            sb.append("%2C").append(nat.COUNTRYCODE);
		}

		return sb.toString();
    }

	public static String createStreetaddressPostalcodeRequest(final Country nat, final ICountrySubdivision pCountrySubdivision, final String pPostalCode, String pStreetName, final String pStreetNumber){
		if(pStreetName == null) {
			pStreetName = ""; // empty streetname is like searching for the town only.
		}

		Assert.assertNotNull(nat);
		Assert.assertTrue(pStreetNumber == null || (pStreetNumber != null && pStreetNumber.length() > 0)); // null or at least 1 char

		final StringBuilder sb = new StringBuilder();

        sb.append("query=");

		if(pStreetNumber != null){
            sb.append(Uri.encode(pStreetNumber)).append("%2C");
		}
        sb.append(Uri.encode(pStreetName));

		if(pCountrySubdivision != null){
            sb.append("%2C").append(pCountrySubdivision);
        }

        sb.append("%2C").append(Uri.encode(pPostalCode));

		if(nat != null) {
            sb.append("%2C").append(nat.COUNTRYCODE);
		}

		return sb.toString();
    }

	public static String createFreeformAddressRequest(final Country nat, final String freeFormAddress){
		Assert.assertNotNull(freeFormAddress);
		Assert.assertTrue(freeFormAddress.length() > 0);

		final StringBuilder sb = new StringBuilder();

        sb.append("query=").append(Uri.encode(freeFormAddress));

		if(nat != null) {
            sb.append("%2C").append(nat.COUNTRYCODE);
		}

		return sb.toString();
    }

}
