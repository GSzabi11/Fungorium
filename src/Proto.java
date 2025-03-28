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
                case "init" -> {
                    if (parts[1].equals("tekton")) {
                        String rawId = parts[2];            // "T1"
                        int id = getIntId(rawId);           // → 1
                        Tekton t = new Tekton(id);
                        tektonok.put(rawId, t);
                        logger.info("Tekton letrehozva: " + rawId);

                    } else if (parts[1].equals("rovar")) {
                        String rawId = parts[2];             // R1
                        String fajtaStr = parts[3].toUpperCase(); // "lila" → "LILA"
                        String tektonId = parts[4];          // T1

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
                        logger.info("Rovar létrehozva: " + rawId + ", fajtaja: " + fajta + ", tekton: " + tektonId);

                    } else if (parts[1].equals("gomba")) {
                        String rawId = parts[2];                 // pl. G1
                        String fajtaStr = parts[3].toUpperCase(); // pl. "piros"
                        String tektonId = parts[4];             // pl. T1

                        Gombafaj fajta;
                        try {
                            fajta = Gombafaj.valueOf(fajtaStr);
                        } catch (IllegalArgumentException e) {
                            logger.warning("Ismeretlen gombafajta: " + fajtaStr);
                            break;
                        }

                        Tekton tekton = tektonok.get(tektonId);
                        if (tekton == null) {
                            logger.warning("Nem létező tekton: " + tektonId);
                            break;
                        }

                        Gomba gomba = new Gomba(fajta, tekton);
                        gombak.put(rawId, gomba); 
                        tekton.setGomba(gomba);   // ketiranyu kapcsolat gomba es tekton kozott
                        logger.info("Gomba letrehozva: " + rawId + ", fajta: " + fajta + ", tekton: " + tektonId);


                    } else if (parts[1].equals("spora")) {
                        String rawId = parts[2];             // pl. S1
                        String tipus = parts[3].toLowerCase();  // pl. benito
                        int tapanyag = 0;

                        // Keresd meg a "tapanyag" kulcsszót és olvasd utána az értéket
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

                        Spora spora = switch (tipus) {
                            case "benito" -> new BenitoSporaElement(tapanyag);
                            case "lassito" -> new LassitoSporaElement(tapanyag);
                            case "gyorsito" -> new GyorsitoSporaElement(tapanyag);
                            case "vagastgatlo" -> new VagastGatloSporaElement(tapanyag);
                            default -> null;
                        };

                        if (spora == null) {
                            logger.warning("Ismeretlen sporatipus: " + tipus);
                            break;
                        }

                        sporak.put(rawId, spora);
                        logger.info("Spora letrehozva: " + rawId + ", tipus: " + tipus.toUpperCase() + ", tapanyag: " + tapanyag);
                    }
                }
                case "rovar" -> {
                    String rovarId = parts[1];
                
                    if (!rovarok.containsKey(rovarId)) {
                        logger.warning("Nem letezo rovar: " + rovarId);
                        break;
                    }
                
                    Rovar rovar = rovarok.get(rovarId);
                
                    if (parts[2].equals("eszik")) {
                        String sporaId = parts[3];
                
                        if (!sporak.containsKey(sporaId)) {
                            logger.warning("Nem letező spóra: " + sporaId);
                            break;
                        }
                
                        Spora spora = sporak.get(sporaId);
                        logger.info("Rovar " + rovarId + " jelenlegi tapanyaga: " + rovar.getTapanyag());
                        rovar.fogyaszt(spora);
                        logger.info("Rovar " + rovarId + " elfogyasztotta a spórát: " + sporaId + " jelenlegi tapanyaga: " + rovar.getTapanyag());
                    }
                }
                
            }

        }
    }
}


