/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.util.sri;

import java.util.StringTokenizer;

public class X500NameGeneral
{
    private String CN;
    private String OU;
    private String O;
    private String L;
    private String ST;
    private String C;
    
    public X500NameGeneral(final String name) {
        this.CN = null;
        this.OU = null;
        this.O = null;
        this.L = null;
        this.ST = null;
        this.C = null;
        final StringTokenizer st = new StringTokenizer(name, ",");
        while (st.hasMoreTokens()) {
            final String token = st.nextToken().trim();
            final int idx = token.indexOf("=");
            if (idx >= 0) {
                final String label = token.substring(0, idx);
                final String value = token.substring(idx + 1);
                if ("CN".equals(label)) {
                    this.CN = value;
                }
                else if ("OU".equals(label)) {
                    this.OU = value;
                }
                else if ("O".equals(label)) {
                    this.O = value;
                }
                else if ("C".equals(label)) {
                    this.C = value;
                }
                else if ("L".equals(label)) {
                    this.L = value;
                }
                else {
                    if (!"ST".equals(label)) {
                        continue;
                    }
                    this.ST = value;
                }
            }
        }
    }
    
    public String getC() {
        return this.C;
    }
    
    public String getCN() {
        return this.CN;
    }
    
    public String getL() {
        return this.L;
    }
    
    public String getO() {
        return this.O;
    }
    
    public String getOU() {
        return this.OU;
    }
    
    public String getST() {
        return this.ST;
    }
}
