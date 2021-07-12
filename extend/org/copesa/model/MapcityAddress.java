package org.copesa.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;

import com.jayway.jsonpath.JsonPath;


public class MapcityAddress {
	/**	Logger */
	private CLogger			log = CLogger.getCLogger (getClass());
	private String nombre_via_largo;
	private double latitud;
	private double longitud;
	private String tipo_via_largo;
	private String altura;
	private String comuna_largo;
	private String provincia_largo;
	private int region_corto;
	private String sectorzona;
	private String sector;
	private String zona;
	private String[] invalid_sectorzona;
	private String codigo_postal;
	private String anexo;
	private String gse;
	private String geocoding;
	private boolean normalized = false;
	private boolean checked = false;
	private static String proxyhost = null;
	private static int proxyport = 0;
	public static final int COPESA_LAYER_ID = 2148;
	public static final int TIMEOUT_VALUE = 10000;
	
	private String[] getZones(String _configvar)
	{
		try
		{
			String zonelist = MSysConfig.getValue(_configvar);
			String[] zones = zonelist.split(",");
			if( Array.getLength(zones) <= 0 )
				return null;
			return zones;
		}
		catch(Exception e)
		{
			log.warning(e.getMessage());
			return null;
		}
	}

	public String[] getZonasSinReparto()
	{
		return getZones("COPESA_ZONAS_SINREPARTO");
	}

	public String[] getZonasSD()
	{
		return getZones("COPESA_ZONAS_SD");
	}

	public String getGse() {
		return gse;
	}

	public void setGse(String gse) {
		this.gse = gse;
	}

	public String getGeocoding() {
		return geocoding;
	}

	public void setGeocoding(String geocoding) {
		this.geocoding = geocoding;
	}
	
	private String getProxyhost() {
		if( proxyhost != null )
			return proxyhost;
		log.info("Obteniendo proxy COPESA");
		try
		{
			//String prop = System.getProperty("proxycopesa","");
			//String prop = DB.getSQLValueString(null, "Select Value from AD_SysConfig where name='COPESA_MAPCITY_PROXY'");
			String prop = MSysConfig.getValue("COPESA_MAPCITY_PROXY");
			log.info("Proxy COPESA: " + prop);
			String[] arr = prop.split(":");
			
			if( Array.getLength(arr) == 2)
			{	
				proxyhost = arr[0];
				proxyport = Integer.parseInt(arr[1]);
			}
			else
			{	
				proxyhost = null;
				proxyport = 0;
			}
			
		}
		catch ( Exception e )
		{
			log.warning(e.getMessage());
			return null;
		}
		return proxyhost;
	}

	private int getProxyport() {
		return (proxyhost != null) ? proxyport : 0;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNormalized() {
		return normalized;
	}

	public void setNormalized(boolean normalized) {
		this.normalized = normalized;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getSectorzona() {
		return sectorzona;
	}

	public void setSectorzona(String sectorzona) {
		this.sectorzona = sectorzona;
        String[] sector_zona_arr = sectorzona.split("-");
        this.setSector(sector_zona_arr[0]);
        this.setZona(sector_zona_arr[1]);
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getTipo_via_largo() {
		return tipo_via_largo;
	}

	public void setTipo_via_largo(String tipo_via_largo) {
		this.tipo_via_largo = tipo_via_largo;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getComuna_largo() {
		return comuna_largo;
	}

	public void setComuna_largo(String comuna_largo) {
		this.comuna_largo = comuna_largo;
	}

	public String getProvincia_largo() {
		return provincia_largo;
	}

	public void setProvincia_largo(String provincia_largo) {
		this.provincia_largo = provincia_largo;
	}

	public int getRegion_corto() {
		return region_corto;
	}

	public void setRegion_corto(int i) {
		this.region_corto = i;
	}

	public String getNombre_via_largo() {
		return nombre_via_largo;
	}

	public void setNombre_via_largo(String nombre_via_largo) {
		this.nombre_via_largo = nombre_via_largo;
	}

	public ZoneType getZoneType(String _sectorzone)
	{
		String[] zonassinreparto = getZonasSinReparto();
		String[] zonasrepartosd = getZonasSD();
		
		if( _sectorzone == null )
			return ZoneType.ZONETYPE_EXCLUDED;
		
		if( zonassinreparto != null && Arrays.asList(zonassinreparto).contains( _sectorzone.trim() ) )
			return ZoneType.ZONETYPE_EXCLUDED;

		if( zonasrepartosd != null && Arrays.asList(zonasrepartosd).contains( _sectorzone.trim() ) )
			return ZoneType.ZONETYPE_SD;
		
		return ZoneType.ZONETYPE_NORMAL;
		
	}

	public ZoneType getZoneType()
	{
		return getZoneType(getSectorzona());
	}

	
	public boolean normalize(String _address)
	{
		try
		{
	        Map<String,Object> params = new LinkedHashMap<String,Object>();
	        params.put("direccion", _address);
	        //params.put("access_token", "068030681e5446bdceeeb301d691d706");
	        params.put("access_token", MSysConfig.getValue("COPESA_MAPCITY_ACCESS_TOKEN"));
	        String response = httpPost("http://services.mapcity.com/nyg_chile/v2/normalizar.json", params);
/*
	        boolean valida = JsonPath.read(response, "$.results[0].caracterizacion.direccion_valida");
	        if (!valida )
	        	return false;
*/	        
	        double latitud = Double.parseDouble(JsonPath.read(response, "$.results[0].location.lat").toString());
	        double longitud = Double.parseDouble(JsonPath.read(response, "$.results[0].location.lng").toString());
	        
	        if( latitud == 0 ||  longitud == 0)
	        	return false;
	        
	        this.setLatitud(latitud);
	        this.setLongitud(longitud);
	        this.setNombre_via_largo( (String)(JsonPath.read(response, "$.results[0].direccion.nombre_via_largo")) );
	        this.setTipo_via_largo((String)(JsonPath.read(response, "$.results[0].direccion.tipo_via_largo")));
	        this.setAltura((String)(JsonPath.read(response, "$.results[0].direccion.altura")));
	        this.setComuna_largo((String)(JsonPath.read(response, "$.results[0].direccion.comuna_largo")));
	        this.setProvincia_largo((String)(JsonPath.read(response, "$.results[0].direccion.provincia_largo")));
	        //this.setRegion_corto((int) (JsonPath.read(response, "$.results[0].direccion.region_corto"))); //ininoles se cambia linea porque genera error en eclipse
	        this.setRegion_corto((Integer) (JsonPath.read(response, "$.results[0].direccion.region_corto")));
	        this.setCodigo_postal((String) (JsonPath.read(response, "$.results[0].caracterizacion.codigo_postal")) );
	        this.setGse((String) (JsonPath.read(response, "$.results[0].caracterizacion.gse")) );
	        this.setGeocoding((String) (JsonPath.read(response, "$.results[0].location.metodo_geocoding")) );
	        this.setAnexo((String)(JsonPath.read(response, "$.results[0].direccion.anexo")));
	        this.setNormalized(true);
	        return true;
	        
		}
		catch (Exception e)
		{
			log.warning(e.getMessage());
			return false;
		}
	}

	private int searchCopesaLayer(String _jsonstr)
	{
		int layerid;
		try
		{
			int total_entries = Integer.parseInt(JsonPath.read(_jsonstr, "$.pagination.total_entries_matching").toString());
			
			for(int i=0; i < total_entries; i++)
			{
				layerid = Integer.parseInt(JsonPath.read(_jsonstr, "$.features[" + i + "].properties.layer_id").toString());
				if( layerid == COPESA_LAYER_ID )
					return i;
			}
		}
		catch(Exception e)
		{
			log.warning(e.getMessage());
		}
		return -1;
	}
	
	public boolean checkCoverage()
	{
		try 
		{
			String latstr = (new Double(this.getLatitud())).toString();
			String lonstr = (new Double(this.getLongitud())).toString();
			String pointstr = "POINT(" + lonstr + "%20" + latstr + ")";;
			String uri = "http://wm20.mapcity.com/layers/2148/features.json";
			
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String datestr = sdf.format(new Date());
			
			int userid = 1017;
			//String api_key = "q71ZZ+9csHcAG4VUym+/jEHLv97aIrJY0MWQuOsbr2mEkPccJkvLfGE0zy2GniTP8CTjxMqYFp0GGqzryRN8AA=="; 
			String api_key = MSysConfig.getValue("COPESA_MAPCITY_API_KEY");
			//String api_key = "w71ZZ+9csHcAG4VUym+/jEHLv97aIrJY0MWQuOsbr2mEkPccJkvLfGE0zy2GniTP8CTjxMqYFp0GGqzryRN8AA=="; 
			//String email = "rgainza@copesa.cl";
			String canonical_string = ",,/layers/2148/features.json?intersects=" + pointstr + "," + datestr;
			byte[] hash = hash_hmac(canonical_string, api_key);
			byte[] encoded_bytes = Base64.encodeBase64(hash);
			
			String hash_base64 = new String(encoded_bytes, "UTF-8");
			
			String authorization = "APIAuth " + userid + ":" + hash_base64;
			
			
	        Map<String,Object> params = new LinkedHashMap<String,Object>();
	        params.put("intersects", pointstr);

	        Map<String, Object> headers = new LinkedHashMap<String,Object>();
	        headers.put("Authorization", authorization);
	        headers.put("Date", datestr);
	        	        
	        String response = httpGet(uri, params, headers);
	        int layerindex = searchCopesaLayer(response);
	        if( layerindex < 0 )
	        	return false;
	        this.setSectorzona((String)(JsonPath.read(response, "$.features[" + layerindex + "].properties.zona")));
	        
	        return this.validateSectorZone(this.getSectorzona());
	        
		}
		catch (Exception e)
		{
			log.warning(e.getMessage());
			return false;
		}
	}

	public boolean validateSectorZone(String _sectorzone)
	{
		try
		{
			this.setSectorzona(_sectorzone);
			
	        if( this.getSectorzona().length() != 5 )
	        	return false;
	        
	        if ( Integer.parseInt(this.getSector()) < 0 ||  Integer.parseInt(this.getSector()) > 99 )
	        	return false;
	
	        if ( Integer.parseInt(this.getZona()) < 0 ||  Integer.parseInt(this.getZona()) > 99 )
	        	return false;
	        
	        this.setChecked(true);
	        
	        invalid_sectorzona = this.getZonasSinReparto();
	        
	        if( invalid_sectorzona != null && Arrays.asList(invalid_sectorzona).contains(this.getSectorzona()) )
	        	return false;
		}
		catch(Exception e)
		{
			log.warning(e.getMessage());
			return false;
		}
		return true;
	}
	
	private byte[] hash_hmac(String data, String key)
	{
		try 
		{
	        // Get an hmac_sha1 key from the raw key bytes
	        byte[] keyBytes = key.getBytes("UTF-8");
	        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

	        // Get an hmac_sha1 Mac instance and initialize with the signing key
	        Mac mac = Mac.getInstance("HmacSHA1");
	        mac.init(signingKey);

	        // Compute the hmac on input data bytes
	        byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
	        return rawHmac;
		}
		catch (Exception e) 
		{
			log.warning(e.getMessage());
			return null;
		}	
	}
	
	private String httpPost(String uri, Map<String, Object> params)
			throws UnsupportedEncodingException, IOException, ProtocolException {
		URL url = new URL(uri);
		StringBuilder querystring = new StringBuilder();
		for (Map.Entry<String,Object> param : params.entrySet()) {
		    if (querystring.length() != 0) querystring.append('&');
		    querystring.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		    querystring.append('=');
		    querystring.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		
		byte[] postDataBytes = querystring.toString().getBytes("UTF-8");

		Proxy proxy = null;
		if( this.getProxyhost() != null )
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.getProxyhost(), this.getProxyport() ));

		HttpURLConnection conn;
		if (proxy != null )
			conn = (HttpURLConnection)url.openConnection(proxy);
		else
			conn = (HttpURLConnection)url.openConnection();

		conn.setConnectTimeout(TIMEOUT_VALUE);
		conn.setReadTimeout(TIMEOUT_VALUE);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

		StringBuilder sb = new StringBuilder();
		int c;
		while((c = in.read()) >= 0) 	
			sb.append((char)c);
		
		String response = sb.toString();
		return response;
	}
	
	private String httpGet(String uri, Map<String, Object> params, Map<String, Object> headers)
			throws UnsupportedEncodingException, IOException, ProtocolException {
		
		Proxy proxy = null;
		if( this.getProxyhost() != null )
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.getProxyhost(), this.getProxyport() ));
		
		StringBuilder querystring = new StringBuilder();
		for (Map.Entry<String,Object> param : params.entrySet()) {
		    if (querystring.length() != 0) querystring.append('&');
		    querystring.append(param.getKey());
		    querystring.append('=');
		    querystring.append(param.getValue());
		}
		
		URL url = new URL(uri + "?" + querystring);

		HttpURLConnection conn;
		if (proxy != null )
			conn = (HttpURLConnection)url.openConnection(proxy);
		else
			conn = (HttpURLConnection)url.openConnection();

		conn.setConnectTimeout(TIMEOUT_VALUE);
		conn.setReadTimeout(TIMEOUT_VALUE);
		conn.setRequestMethod("GET");
		for (Map.Entry<String,Object> header : headers.entrySet()) 
		{
			conn.setRequestProperty(header.getKey(), header.getValue().toString());
			log.info(header.getValue().toString());
		}
		
		conn.setDoOutput(true);
		
		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

		StringBuilder sb = new StringBuilder();
		int c;
		while((c = in.read()) >= 0) 	
			sb.append((char)c);
		
		String response = sb.toString();
		return response;
	}	
	
}
