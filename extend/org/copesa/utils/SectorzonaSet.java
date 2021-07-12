package org.copesa.utils;

public class SectorzonaSet {
	private String[] sectorzonas;
	
	public SectorzonaSet(String _Rangestr) throws Exception
	{
		this.setSectorzonas(toArray(_Rangestr));
	}
	
	private String[] toArray(String _Rangestr) throws Exception
	{
		String arr[] = _Rangestr.split(",");
		
		for(int i=0; i < arr.length; i++ )
		{	
		     arr[i] = arr[i].trim();
		     if( !validateSectorzonaRange(arr[i]) )
		    	 throw new Exception("Formato inválido de sectorzona");
		}	
		return arr;    	
	}
		
	public boolean contains(String sectorzona) throws Exception
	{
		String arr[] = this.getSectorzonas();
		
		for(int i=0; i < arr.length; i++ )
		{	
			if( matchSectorzone(arr[i], sectorzona) )
				return true;
		}	
		return false;
	}

	public String[] getSectorzonas() {
		return sectorzonas;
	}

	public void setSectorzonas(String[] sectorzona) {
		this.sectorzonas = sectorzona;
	}
	
	private boolean matchSectorzone(String range, String sz) throws Exception
	{
		String sectorzona[] = range.split(":");
		
		if( sectorzona.length == 1 )
			return sz == range;
		
		int lowend = Integer.parseInt(sectorzona[0].replaceFirst("-", ""));
		int highend = Integer.parseInt(sectorzona[1].replaceFirst("-", ""));
		int number = Integer.parseInt(sz.replaceFirst("-", ""));
		
		return number >= lowend && number <= highend;
	}
	
	private boolean validateSectorzonaRange(String szrange) throws Exception
	{
		String arr[] = szrange.split(":");

		if( arr.length > 2 )
			return false;
		
		for(int i=0; i < arr.length; i++ )
		{
			if( !validateSectorzona(arr[i]) )
				return false;
		}
		
		return true;
		
	}
	
	private boolean validateSectorzona(String sz) throws Exception
	{
		String arr[] = sz.split("-");
		
		if( arr.length != 2 )
			return false;

		int sector = Integer.parseInt(arr[0]);
		int zona = Integer.parseInt(arr[1]);
		
		return (sector >= 0 && sector <= 99 && zona >= 0 && zona <= 99);
		
	}
	
}
