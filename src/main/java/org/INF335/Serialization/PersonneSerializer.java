package org.INF335.Serialization;

import org.INF335.Personne;
import org.INF335.ReadWriteBuffer;

import java.nio.charset.StandardCharsets;
import java.security.spec.EncodedKeySpec;
import java.time.LocalDate;

public class PersonneSerializer {

    private final static int PERSONNE_PREFIX = 0x80980000;

    private enum Tag {
        Nom( PERSONNE_PREFIX | 0x0001 ),
        DateDeNaissance( PERSONNE_PREFIX | 0x0002 ),
        SalaireHoraire( PERSONNE_PREFIX | 0x0003 ),
        HeureTravailSemaine( PERSONNE_PREFIX | 0x0004 ),
        NbSemainesDeTravail( PERSONNE_PREFIX | 0x0005 ),

        UNKNOWN( 0xFFFFFFFF );

        public final int value;
        private Tag(int val) {
            this.value = val;
        }

        public static Tag fromInt(int v) {
            for(Tag t : Tag.values()) if(t.value == v) return t;
            return UNKNOWN;
        }
    }

    private static void writeWithTagAndSize(Tag tag, byte[] data, ReadWriteBuffer rw){
        rw.write( Encodage.encodeInt(tag.value) );
        rw.write( Encodage.encodeInt(data.length));
        rw.write(data);
    }

    private static int sizeWithTagAndSize(byte[] data) {
        return 8 + data.length;
    }

    private static int sizeWithTagAndSize(byte[]... buffers) {
        int total = 0;
        for(byte[] b : buffers) {
            total += sizeWithTagAndSize(b);
        }

        return total;
    }

    public static void serializePersonne(Personne p, ReadWriteBuffer rw) {
        byte[] nom = p.getNom().getBytes(StandardCharsets.UTF_8);
        byte[] dob = Encodage.encodeLocalDate(p.getDateDeNaissance());
        byte[] salaire = Encodage.encodeDouble(p.getSalaireHoraire());
        byte[] heures = Encodage.encodeDouble(p.getHeureTravailSemaine());
        byte[] nbSemaines = Encodage.encodeInt(p.getNbSemainesDeTravail());

        rw.write(Encodage.encodeInt( sizeWithTagAndSize(nom, dob, salaire, heures, nbSemaines) ));
        writeWithTagAndSize(Tag.Nom, nom, rw);
        writeWithTagAndSize(Tag.DateDeNaissance, dob, rw);
        writeWithTagAndSize(Tag.SalaireHoraire, salaire, rw);
        writeWithTagAndSize(Tag.HeureTravailSemaine, heures, rw);
        writeWithTagAndSize(Tag.NbSemainesDeTravail, nbSemaines, rw);
    }

    public static Personne deserializePersonne(ReadWriteBuffer rw) {
        int toRead = Encodage.readInt(rw);
        int read = 0;

        Personne p = new Personne();

        while(read < toRead) {
            Tag tag = Tag.fromInt( Encodage.readInt(rw) );
            int size = Encodage.readInt(rw);
            byte[] data = new byte[size];
            rw.read(data);
            read += 8 + size;

            switch(tag) {
                case Nom:
                    p.setNom(new String(data, StandardCharsets.UTF_8));
                    break;
                case DateDeNaissance:
                    p.setDateDeNaissance(Encodage.decodeLocalDate(data));
                    break;
                case SalaireHoraire:
                    p.setSalaireHoraire(Encodage.decodeDouble(data));
                    break;
                case HeureTravailSemaine:
                    p.setHeureTravailSemaine(Encodage.decodeDouble(data));
                    break;
                case NbSemainesDeTravail:
                    p.setNbSemainesDeTravail(Encodage.decodeInt(data));
                    break;
            }
        }



        return p;
    }

}
