import Fugorium_model.*;
import java.util.*;
import java.util.logging.*;
import java.io.*;


public class Proto {

    private static int getIntId(String rawId) {
        return Integer.parseInt(rawId.substring(1));
    }

    static Logger logger = Logger.getLogger("ProtoLogger");
    static Map<String, Tekton> tektonok = new HashMap<>();
    static Map<String, Rovar> rovarok = new HashMap<>();
    static Map<String, Gomba> gombak = new HashMap<>();
    static Map<String, Spora> sporak = new HashMap<>();
    static Map<String, Gombafonal> fonalak = new HashMap<>();

    public static void main(String[] args) throws Exception {

        // Log fajlba iranyitasa
        FileHandler fh = new FileHandler("proto.log", false);
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
        fh.setFormatter(new SimpleFormatter());

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            switch (parts[0]) {
                case "init":
                    if (parts[1].equals("tekton")) {
                        String rawId = parts[2];
                        int id = getIntId(rawId);
                        Tekton t = new Tekton(id);
                        tektonok.put(rawId, t);
                        logger.info("Tekton letrehozva: " + rawId);
                    } else if (parts[1].equals("rovar")) {
                        String rawId = parts[2];
                        String fajtaStr = parts[3].toUpperCase();
                        String tektonId = parts[4];
            
                        Rovarfaj fajta;
                        try {
                            fajta = Rovarfaj.valueOf(fajtaStr);
                        } catch (IllegalArgumentException e) {
                            logger.warning("Ismeretlen rovarfajta: " + fajtaStr);
                            break;
                        }
            
                        Tekton helyzet = tektonok.get(tektonId);
                        if (helyzet == null) {
                            logger.warning("Nem letezo tekton: " + tektonId);
                            break;
                        }
            
                        Rovar r = new Rovar(fajta, helyzet);
                        rovarok.put(rawId, r);
                        logger.info("Rovar letrehozva: " + rawId + ", fajtaja: " + fajta + ", tekton: " + tektonId);
                    } else if (parts[1].equals("gomba")) {
                        String rawId = parts[2];
                        String fajtaStr = parts[3].toUpperCase();
                        String tektonId = parts[4];
            
                        Gombafaj fajta;
                        try {
                            fajta = Gombafaj.valueOf(fajtaStr);
                        } catch (IllegalArgumentException e) {
                            logger.warning("Ismeretlen gombafajta: " + fajtaStr);
                            break;
                        }
            
                        Tekton tekton = tektonok.get(tektonId);
                        if (tekton == null) {
                            logger.warning("Nem letezo tekton: " + tektonId);
                            break;
                        }
            
                        Gomba gomba = new Gomba(fajta, tekton);
                        gombak.put(rawId, gomba);
                        tekton.setGomba(gomba);
                        logger.info("Gomba letrehozva: " + rawId + ", fajta: " + fajta + ", tekton: " + tektonId);
                    } else if (parts[1].equals("spora")) {
                        String rawId = parts[2];
                        String tipus = parts[3].toLowerCase();
                        int tapanyag = 0;
            
                        for (int i = 4; i < parts.length - 1; i++) {
                            if (parts[i].equalsIgnoreCase("tapanyag")) {
                                try {
                                    tapanyag = Integer.parseInt(parts[i + 1]);
                                } catch (NumberFormatException e) {
                                    logger.warning("Ervenytelen tapanyag ertek: " + parts[i + 1]);
                                }
                                break;
                            }
                        }
            
                        Spora spora = null;
                        switch (tipus) {
                            case "benito" -> spora = new BenitoSporaElement(tapanyag);
                            case "lassito" -> spora = new LassitoSporaElement(tapanyag);
                            case "gyorsito" -> spora = new GyorsitoSporaElement(tapanyag);
                            case "vagastgatlo" -> spora = new VagastGatloSporaElement(tapanyag);
                            case "osztodo" -> spora = new RovarOsztodoSporaElement(tapanyag);
                            default -> {
                                logger.warning("Ismeretlen sporatipus: " + tipus);
                                break;
                            }
                        }
            
                        if (spora != null) {
                            sporak.put(rawId, spora);
                            logger.info("Spora letrehozva: " + rawId + ", tipus: " + tipus.toUpperCase() + ", tapanyag: " + tapanyag);
                        }
                    }
                    break;                
                
                    case "rovar":
                    if (parts.length == 2 && parts[1].equals("eszik")) {
                        logger.warning("Na de mit eszik a rovar?? Hianyzik a spora ID!");
                        break;
                    }
            
                    String rovarId = parts[1];
                    if (!rovarok.containsKey(rovarId)) {
                        logger.warning("Nem letezo rovar: " + rovarId);
                        break;
                    }
            
                    Rovar rovar = rovarok.get(rovarId);
            
                    if (parts[2].equals("eszik")) {
                        String sporaId = parts[3];
                        if (!sporak.containsKey(sporaId)) {
                            logger.warning("Nem letezo spora: " + sporaId);
                            break;
                        }
            
                        Spora spora = sporak.get(sporaId);
                        logger.info("Rovar " + rovarId + " jelenlegi tapanyaga: " + rovar.getTapanyag());
                        rovar.fogyaszt(spora);
                        logger.info("Rovar " + rovarId + " elfogyasztotta a sporat: " + sporaId + " jelenlegi tapanyaga: " + rovar.getTapanyag());
            
                    } else if (parts[2].equals("allapot")) {
                        HashMap<RovarAllapot, Integer> allapotok = rovar.getAllapotMap();
                        logger.info("Rovar " + rovarId + " allapotai:");
                        for (Map.Entry<RovarAllapot, Integer> entry : allapotok.entrySet()) {
                            if (entry.getValue() > 0) {
                                logger.info("  " + entry.getKey() + ": " + entry.getValue());
                            }
                        }
                    }
                    break;

                
                case "gombafonal":
                    String fonalId = parts[1];
                
                    if (!fonalak.containsKey(fonalId)) {
                        logger.warning("Nincs ilyen gombafonal: " + fonalId);
                        break;
                    }
                
                    Gombafonal fonal = fonalak.get(fonalId);
                
                    if (parts.length >= 4 && parts[2].equals("tovabbno") && parts[3].equals("tekton")) {
                        String celTektonId = parts[4];
                        Tekton cel = tektonok.get(celTektonId);
                        if (cel != null) {
                            fonal.novekszik(cel);
                            logger.info("Gombafonal " + fonalId + " tovabb nott " + celTektonId + "-re.");
                        } else {
                            logger.warning("Celtekton nem letezik: " + celTektonId);
                        }
                    } else {
                        logger.warning("Hibas 'gombafonal tovabbno' parancs.");
                    }
                    break;

                case "tekton":
                    String tektonId = parts[1];

                    Tekton eredeti = tektonok.get(tektonId);
                    if (eredeti == null) {
                        logger.warning("Nincs ilyen tekton: " + tektonId);
                        break;
                    }
                
                    if (parts[2].equals("torik")) {
                        // Új ID meghatarozasa
                        int maxId = tektonok.values().stream()
                            .mapToInt(Tekton::getId)
                            .max()
                            .orElse(1);
                
                        int ujId = maxId + 1;
                        Tekton uj = new Tekton(ujId);
                
                        eredeti.kettetor(uj);
                
                        String ujNev = "T" + ujId;
                        tektonok.put(ujNev, uj);
                        logger.info("Tekton " + tektonId + " kettetort -> uj tekton: " + ujNev);
                    }
                
                    else if (parts[2].equals("szomszedok")) {
                        logger.info("Tekton " + tektonId + " szomszedai:");
                        for (Tekton sz : eredeti.getSzomszedok()) {
                            logger.info("  T" + sz.getId());
                        }
                    }
                
                    break;

                case "gomba":
                    String gombaId = parts[1];

                    if (!gombak.containsKey(gombaId)) {
                        logger.warning("Nincs ilyen gomba: " + gombaId);
                        break;
                    }

                    Gomba g = gombak.get(gombaId);

                    switch (parts[2]) {
                        case "sporat":
                            if (parts.length >= 4 && parts[3].equals("termel")) {
                                g.sporaTermel();
                                logger.info("Gomba " + gombaId + " sporat termelt.");
                            } else {
                                logger.warning("Hibas parancs: hianyzik a 'termel' kulcsszo.");
                            }
                            break;

                        case "sporaz":
                            g.sporaz();
                            logger.info("Gomba " + gombaId + " sporaz.");
                            break;

                        case "gombafonalat":
                        if (parts.length >= 7 && parts[4].equals("noveszt") && parts[5].equals("tekton")) {
                            String fonalID = parts[3];
                            String celTektonId = parts[6];
                            Tekton cel = tektonok.get(celTektonId);
                            if (cel != null) {
                                Gombafonal ujFonal = g.novesztUjFonal(cel);
                                if (ujFonal != null) {
                                    fonalak.put(fonalID, ujFonal);
                                    logger.info("Gomba " + gombaId + " uj fonalat novesztett (azonosito: " + fonalID + "): " + "T" + g.getTekton().getId() + " -> T" + celTektonId);
                                } else {
                                    logger.warning("Gomba " + gombaId + " nem tudott fonalat noveszteni T" + celTektonId + "-re: nem szomszédos!");
                                }
                            } else {
                                logger.warning("Celtekton nem letezik: " + celTektonId);
                            }
                            } else {
                                logger.warning("Hibas 'gombafonalat noveszt' parancs.");
                            }
                            break;

                    default:
                        logger.warning("Ismeretlen gomba parancs: " + parts[2]);
                        break;
                }
                break;

            }

        }
    }
}


