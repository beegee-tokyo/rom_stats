/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.romstats;

import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.MessageDigest;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;

public class Utilities {
	public static final String SETTINGS_PREF_NAME = "ROMStats";
	public static final String TAG = "ROMStats";

	public static String getUniqueID(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

		String device_id = digest(tm.getDeviceId());
		if (device_id == null) {
			String wifiInterface = SystemProperties.get("wifi.interface");
			try {
				String wifiMac = new String(NetworkInterface.getByName(wifiInterface).getHardwareAddress());
				device_id = digest(wifiMac);
			} catch (Exception e) {
				device_id = null;
			}
		}

		return device_id;
	}

	public static String getStatsUrl() {
		String returnUrl = SystemProperties.get("ro.romstats.url");

		if (returnUrl.isEmpty()) {
			return null;
		}
		
		// if the last char of the link is not /, add it
		if (!returnUrl.substring(returnUrl.length() - 1).equals("/")) {
			returnUrl += "/";
		}

		return returnUrl;
	}

	public static String getCarrier(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		String carrier = tm.getNetworkOperatorName();
		if ("".equals(carrier)) {
			carrier = "Unknown";
		}
		return carrier;
	}

	public static String getCarrierId(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		String carrierId = tm.getNetworkOperator();
		if ("".equals(carrierId)) {
			carrierId = "0";
		}
		return carrierId;
	}

	public static String getCountryCode(Context ctx) {

           String[] world = new String[] {
	            "ad", "Andorra, Principality of",
	            "ae", "United Arab Emirates",
	            "af", "Afghanistan, Islamic State of",
	            "ag", "Antigua and Barbuda",
	            "ai", "Anguilla",
	            "al", "Albania",
	            "am", "Armenia",
	            "an", "Netherlands Antilles",
	            "ao", "Angola",
	            "aq", "Antarctica",
	            "ar", "Argentina",
	            "as", "American Samoa",
	            "at", "Austria",
	            "au", "Australia",
	            "aw", "Aruba",
	            "az", "Azerbaidjan",
	            "ba", "Bosnia-Herzegovina",
	            "bb", "Barbados",
	            "bd", "Bangladesh",
	            "be", "Belgium",
	            "bf", "Burkina Faso",
	            "bg", "Bulgaria",
	            "bh", "Bahrain",
	            "bi", "Burundi",
	            "bj", "Benin",
	            "bm", "Bermuda",
	            "bn", "Brunei Darussalam",
	            "bo", "Bolivia",
	            "br", "Brazil",
	            "bs", "Bahamas",
	            "bt", "Bhutan",
	            "bv", "Bouvet Island",
	            "bw", "Botswana",
	            "by", "Belarus",
	            "bz", "Belize",
	            "ca", "Canada",
	            "cc", "Cocos (Keeling) Islands",
	            "cf", "Central African Republic",
	            "cd", "Congo, The Democratic Republic of the",
	            "cg", "Congo",
	            "ch", "Switzerland",
	            "ci", "Ivory Coast (Cote D'Ivoire)",
	            "ck", "Cook Islands",
	            "cl", "Chile",
	            "cm", "Cameroon",
	            "cn", "China",
	            "co", "Colombia",
	            "cr", "Costa Rica",
	            "cs", "Former Czechoslovakia",
	            "cu", "Cuba",
	            "cv", "Cape Verde",
	            "cx", "Christmas Island",
	            "cy", "Cyprus",
	            "cz", "Czech Republic",
	            "de", "Germany",
	            "dj", "Djibouti",
	            "dk", "Denmark",
	            "dm", "Dominica",
	            "do", "Dominican Republic",
	            "dz", "Algeria",
	            "ec", "Ecuador",
	            "ee", "Estonia",
	            "eg", "Egypt",
	            "eh", "Western Sahara",
	            "er", "Eritrea",
	            "es", "Spain",
	            "et", "Ethiopia",
	            "fi", "Finland",
	            "fj", "Fiji",
	            "fk", "Falkland Islands",
	            "fm", "Micronesia",
	            "fo", "Faroe Islands",
	            "fr", "France",
	            "fx", "France (European Territory)",
	            "ga", "Gabon",
	            "gb", "Great Britain",
	            "gd", "Grenada",
	            "ge", "Georgia",
	            "gf", "French Guyana",
	            "gh", "Ghana",
	            "gi", "Gibraltar",
	            "gl", "Greenland",
	            "gm", "Gambia",
	            "gn", "Guinea",
	            "gp", "Guadeloupe (French)",
	            "gq", "Equatorial Guinea",
	            "gr", "Greece",
       		    "gs", "S. Georgia & S. Sandwich Isls.",
       		    "gt", "Guatemala",
	       	    "gu", "Guam (USA)",
       		    "gw", "Guinea Bissau",
        	    "gy", "Guyana",
        	    "hk", "Hong Kong",
        	    "hm", "Heard and McDonald Islands",
        	    "hn", "Honduras",
        	    "hr", "Croatia",
        	    "ht", "Haiti",
        	    "hu", "Hungary",
        	    "id", "Indonesia",
        	    "ie", "Ireland",
        	    "il", "Israel",
        	    "in", "India",
        	    "io", "British Indian Ocean Territory",
        	    "iq", "Iraq",
        	    "ir", "Iran",
        	    "is", "Iceland",
        	    "it", "Italy",
        	    "jm", "Jamaica",
        	    "jo", "Jordan",
        	    "jp", "Japan",
        	    "ke", "Kenya",
        	    "kg", "Kyrgyz Republic (Kyrgyzstan)",
        	    "kh", "Cambodia, Kingdom of",
        	    "ki", "Kiribati",
        	    "km", "Comoros",
        	    "kn", "Saint Kitts & Nevis Anguilla",
        	    "kp", "North Korea",
        	    "kr", "South Korea",
        	    "kw", "Kuwait",
        	    "ky", "Cayman Islands",
        	    "kz", "Kazakhstan",
        	    "la", "Laos",
        	    "lb", "Lebanon",
        	    "lc", "Saint Lucia",
        	    "li", "Liechtenstein",
        	    "lk", "Sri Lanka",
	            "lr", "Liberia",
	            "ls", "Lesotho",
	            "lt", "Lithuania",
	            "lu", "Luxembourg",
	            "lv", "Latvia",
	            "ly", "Libya",
	            "ma", "Morocco",
	            "mc", "Monaco",
	            "md", "Moldavia",
	            "mg", "Madagascar",
	            "mh", "Marshall Islands",
	            "mk", "Macedonia",
	            "ml", "Mali",
	            "mm", "Myanmar",
	            "mn", "Mongolia",
	            "mo", "Macau",
	            "mp", "Northern Mariana Islands",
	            "mq", "Martinique (French)",
	            "mr", "Mauritania",
	            "ms", "Montserrat",
	            "mt", "Malta",
	            "mu", "Mauritius",
	            "mv", "Maldives",
	            "mw", "Malawi",
	            "mx", "Mexico",
	            "my", "Malaysia",
	            "mz", "Mozambique",
	            "na", "Namibia",
	            "nc", "New Caledonia (French)",
	            "ne", "Niger",
	            "nf", "Norfolk Island",
	            "ng", "Nigeria",
	            "ni", "Nicaragua",
	            "nl", "Netherlands",
	            "no", "Norway",
	            "np", "Nepal",
	            "nr", "Nauru",
	            "nt", "Neutral Zone",
	            "nu", "Niue",
	            "nz", "New Zealand",
	            "om", "Oman",
	            "pa", "Panama",
	            "pe", "Peru",
	            "pf", "Polynesia (French)",
	            "pg", "Papua New Guinea",
	            "ph", "Philippines",
	            "pk", "Pakistan",
	            "pl", "Poland",
	            "pm", "Saint Pierre and Miquelon",
	            "pn", "Pitcairn Island",
	            "pr", "Puerto Rico",
	            "pt", "Portugal",
	            "pw", "Palau",
	            "py", "Paraguay",
	            "qa", "Qatar",
	            "re", "Reunion (French)",
	            "ro", "Romania",
	            "ru", "Russian Federation",
	            "rw", "Rwanda",
	            "sa", "Saudi Arabia",
	            "sb", "Solomon Islands",
	            "sc", "Seychelles",
	            "sd", "Sudan",
	            "se", "Sweden",
	            "sg", "Singapore",
	            "sh", "Saint Helena",
	            "si", "Slovenia",
	            "sj", "Svalbard and Jan Mayen Islands",
	            "sk", "Slovak Republic",
	            "sl", "Sierra Leone",
	            "sm", "San Marino",
	            "sn", "Senegal",
	            "so", "Somalia",
	            "sr", "Suriname",
	            "st", "Saint Tome (Sao Tome) and Principe",
	            "su", "Former USSR",
	            "sv", "El Salvador",
	            "sy", "Syria",
	            "sz", "Swaziland",
	            "tc", "Turks and Caicos Islands",
	            "td", "Chad",
	            "tf", "French Southern Territories",
	            "tg", "Togo",
	            "th", "Thailand",
	            "tj", "Tadjikistan",
	            "tk", "Tokelau",
	            "tm", "Turkmenistan",
	            "tn", "Tunisia",
	            "to", "Tonga",
	            "tp", "East Timor",
	            "tr", "Turkey",
	            "tt", "Trinidad and Tobago",
	            "tv", "Tuvalu",
	            "tw", "Taiwan",
	            "tz", "Tanzania",
	            "ua", "Ukraine",
	            "ug", "Uganda",
	            "uk", "United Kingdom",
	            "um", "USA Minor Outlying Islands",
	            "us", "United States",
	            "uy", "Uruguay",
	            "uz", "Uzbekistan",
	            "va", "Holy See (Vatican City State)",
	            "vc", "Saint Vincent & Grenadines",
	            "ve", "Venezuela",
	            "vg", "Virgin Islands (British)",
	            "vi", "Virgin Islands (USA)",
	            "vn", "Vietnam",
	            "vu", "Vanuatu",
	            "wf", "Wallis and Futuna Islands",
	            "ws", "Samoa",
	            "ye", "Yemen",
	            "yt", "Mayotte",
	            "yu", "Yugoslavia",
	            "za", "South Africa",
	            "zm", "Zambia",
	            "zr", "Zaire",
	            "zw", "Zimbabwe",        };

		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = tm.getNetworkCountryIso();
		if (countryCode.equals("")) {
			countryCode = "Unknown";
		}
                String countryName = countryCode;
		for (int i = 0; i < world.length; i += 2) {
                   if (world[i].equals(countryCode)) {
                      countryName = world[i+1];
                   }
                }
		return countryName;
	}

	public static String getDevice() {
		return SystemProperties.get("ro.product.model");
	}

	public static String getModVersion() {
		return SystemProperties.get("ro.build.display.id");
	}

	public static String getRomName() {
		return SystemProperties.get("ro.romstats.name");
	}

	public static String getRomVersion() {
		return SystemProperties.get("ro.romstats.version");
	}
	
	public static long getTimeFrame() {
		String tFrameStr = SystemProperties.get("ro.romstats.tframe", "7");
		return Long.valueOf(tFrameStr);
	}

	public static String digest(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return new BigInteger(1, md.digest(input.getBytes())).toString(16).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}
}
