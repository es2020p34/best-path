package pt.ua.deti.es.p34.statistics;

/**
 * Hello world!
 *
 */
public class App 
{
    /*public static void main( String[] args ) throws Exception
    {
        String csvFile = "./test.csv";
        String line = "";
        String cvsSplitBy = ",";
        HashMap<String, Location> hm = new HashMap<String, Location>();
        JSONParser parser = new JSONParser();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); //IGNORAR PRIMEIRA LINHA
            while ((line = br.readLine()) != null) {
                String[] vehicle = line.split(cvsSplitBy);

                if(!hm.containsKey(vehicle[2])){
                    hm.put(vehicle[2], new Location(vehicle[2]));
                }

                hm.get(vehicle[2]).it.add(new Item(vehicle[4], vehicle[5], vehicle[1]));            
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String i : hm.keySet()) {
            
            String add= getAddress(hm.get(i).it.get(0));
            
            JSONObject obj = (JSONObject)parser.parse(add);
            
            if(obj.containsKey("features")){
                String obj2 = (String)((JSONObject)((JSONObject)((JSONArray)obj.get("features")).get(0)).get("properties")).get("street");
                hm.get(i).street = obj2;
            }else {
                hm.get(i).street = "NONAME";
            }
            hm.get(i).nCars = hm.get(i).it.size();
      }



      for (String i : hm.keySet()){
            // SO PRA VER QUE TRA TUDO NESTE OBJECTO
            System.out.println("LOCATION ID " + hm.get(i).location + " -> CARROS " +hm.get(i).it.size());
            System.out.println("Street " + hm.get(i).street);

      }
        


        
    }

    private final static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String getAddress (Item it) throws Exception {
        String result ="" ;
        HttpGet request = new HttpGet("https://api.geoapify.com/v1/geocode/reverse?lat="+it.lat+"&lon="+it.lon+"&apiKey=48509a8cee3f434fa596049a786d9490");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
                //System.out.println(result);
            }
        }
        return result;
    }*/
}
