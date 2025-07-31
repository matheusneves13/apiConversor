package br.srm.wefin.apiConversor.model;

public enum TipoMoeda {
	OURO_REAL(1),TIBAR(2);
	
	private final int codigo;
	
	TipoMoeda(int tipo){
		this.codigo = tipo;
	}
	
    public int getCodigo() {
        return codigo;
    }
    
    public static TipoMoeda fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (TipoMoeda tipo : TipoMoeda.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de moeda inválido: " + codigo);
    }
}
