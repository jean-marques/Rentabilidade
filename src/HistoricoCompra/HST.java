package HistoricoCompra;

import JDBC.FabConexao;
import RMD.RMD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HST {
    private int produto;
    private double custo;
    private int ordem;
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public int getProduto() {
        return produto;
    }

    public void setProduto(int produto) {
        this.produto = produto;
    }



        public static HST getHST (HST hst, int empresa ) throws SQLException{
            Connection con = FabConexao.conRET();
            String sql_con = """
                        SELECT
                    	top (1)
                        Z.PRODUTO                 AS PRODUTO,
                        A.DESCRICAO,
                    
                        Z.CUSTO_FINAL_LIQUIDO,
                    
                        Z.ORDEM
                    
                        FROM PRODUTOS A WITH(NOLOCK)
                    
                        JOIN (
                    
                        SELECT X.PRODUTO,
                        X.DATA_HORA,
                        X.DESCONTO,
                        X.FORMULARIO_ORIGEM,
                        X.TAB_MASTER_ORIGEM,
                        X.REG_MASTER_ORIGEM,
                        X.EMPRESA,
                        X.CHAVE,
                        X.SEQUENCIA,
                        X.PRECO_COMPRA,
                        X.VALOR_IPI,
                        X.ICMS_SUBST_VALOR,
                        X.ICMS_SUBST_VALOR_PAGAR,
                        X.ICMS_ST_RETIDO_VALOR,
                        X.ICMS_CREDITO,
                        X.DESPESAS,
                        X.FRETE,
                        X.SEGURO,
                        X.REPASSE,
                        X.VALOR_PIS,
                        X.VALOR_COFINS,
                        X.CUSTO,
                        X.CUSTO_FINAL_LIQUIDO,
                        X.QUANTIDADE_ESTOQUE,
                        ROW_NUMBER() OVER ( PARTITION BY  X.PRODUTO ORDER BY X.PRODUTO, X.DATA_HORA DESC, X.SEQUENCIA, X.CHAVE DESC )  AS ORDEM
                        FROM (
                        SELECT B.PRODUTO,
                        A.MOVIMENTO AS DATA_HORA,
                        A.FORMULARIO_ORIGEM,
                        A.TAB_MASTER_ORIGEM,
                        A.NF_COMPRA AS REG_MASTER_ORIGEM,
                        A.EMPRESA,
                        B.NF_COMPRA_PRODUTO AS CHAVE,
                        CASE WHEN B.TOTAL_DESCONTO > 0
                        THEN CASE WHEN B.TIPO_DESCONTO = 3
                        THEN B.DESCONTO
                        ELSE ROUND( ( 1 - (B.TOTAL_PRODUTO / (B.QUANTIDADE * B.VALOR_UNITARIO) ) ) * 100 , 2 )
                        END
                        ELSE 0
                        END                           AS DESCONTO,
                        D.VALOR_PRODUTO_LIQUIDO AS PRECO_COMPRA,
                        D.VALOR_IPI,
                        D.ICMS_SUBST_VALOR,
                        D.ICMS_SUBST_VALOR_PAGAR,
                        D.ICMS_CREDITO,
                        ISNULL(D.ICMS_ST_RETIDO_VALOR,0) AS ICMS_ST_RETIDO_VALOR,
                        D.DESPESAS,
                        D.FRETE,
                        D.SEGURO,
                        D.REPASSE,
                        D.VALOR_PIS,
                        D.VALOR_COFINS,
                        D.CUSTO,
                        D.CUSTO_FINAL_LIQUIDO,
                        B.QUANTIDADE_ESTOQUE AS QUANTIDADE_ESTOQUE,
                        1 AS SEQUENCIA
                        FROM NF_COMPRA                       A WITH(NOLOCK)
                        JOIN NF_COMPRA_PRODUTOS              B WITH(NOLOCK) ON B.NF_COMPRA         = A.NF_COMPRA
                        JOIN OPERACOES_FISCAIS               C WITH(NOLOCK) ON C.OPERACAO_FISCAL   = B.OPERACAO_FISCAL
                        JOIN ESPELHO                         D WITH(NOLOCK) ON D.NF_COMPRA_PRODUTO = B.NF_COMPRA_PRODUTO
                        WHERE 1 = 1
                        AND C.TIPO_OPERACAO   IN (1,8,10)
                        AND B.PRODUTO = ?
                    	and A.empresa = ?
                        UNION ALL
                        SELECT A.PRODUTO,
                        B.DATA_HORA AS MOVIMENTO,
                        B.FORMULARIO_ORIGEM,
                        B.TAB_MASTER_ORIGEM,
                        B.ALTERACAO_CUSTO         AS REG_MASTER_ORIGEM,
                        A.EMPRESA,
                        A.ALTERACAO_CUSTO_EMPRESA AS CHAVE,
                        0 AS DESCONTO,
                        A.CUSTO_NOVO AS PRECO_COMPRA,
                        0 AS VALOR_IPI,
                        0 AS ICMS_SUBST_VALOR,
                        0 AS ICMS_SUBST_VALOR_PAGAR,
                        0 AS ICMS_ST_RETIDO_VALOR,
                        0 AS ICMS_CREDITO,
                        0 AS DESPESAS,
                        0 AS FRETE,
                        0 AS SEGURO,
                        0 AS REPASSE,
                        0 AS VALOR_PIS,
                        0 AS VALOR_COFINS,
                        A.CUSTO_NOVO AS CUSTO,
                        A.CUSTO_NOVO AS  CUSTO_FINAL_LIQUIDO,
                        NULL AS QUANTIDADE_ESTOQUE,
                        2 AS SEQUENCIA
                        FROM ALTERACOES_CUSTOS_EMPRESAS  A WITH(NOLOCK)
                        JOIN ALTERACOES_CUSTOS           B WITH(NOLOCK) ON B.ALTERACAO_CUSTO = A.ALTERACAO_CUSTO
                        WHERE 1 = 1
                        AND A.PRODUTO = ?
                    	and A.EMPRESA = ?
                        UNION ALL
                        SELECT A.PRODUTO,
                        '01/01/1900' AS MOVIMENTO,
                        NULL AS FORMULARIO_ORIGEM,
                        NULL AS TAB_MASTER_ORIGEM,
                        NULL AS REG_MASTER_ORIGEM,
                        NULL AS EMPRESA,
                        A.PRODUTO AS CHAVE,
                        0 AS DESCONTO,
                        A.CUSTO AS PRECO_COMPRA,
                        0 AS VALOR_IPI,
                        0 AS ICMS_SUBST_VALOR,
                        0 AS ICMS_SUBST_VALOR_PAGAR,
                        0 AS ICMS_ST_RETIDO_VALOR,
                        0 AS ICMS_CREDITO,
                        0 AS DESPESAS,
                        0 AS FRETE,
                        0 AS SEGURO,
                        0 AS REPASSE,
                        0 AS VALOR_PIS,
                        0 AS VALOR_COFINS,
                        A.CUSTO   AS CUSTO,
                        A.CUSTO AS  CUSTO_FINAL_LIQUIDO,
                        NULL AS QUANTIDADE_ESTOQUE,
                        4 AS SEQUENCIA
                        FROM CUSTO_INICIAL A WITH(NOLOCK)
                        WHERE 1 = 1
                        AND A.PRODUTO = ?
                        )  X
                        ) Z ON Z.PRODUTO            = A.PRODUTO
                        LEFT JOIN FORMULARIOS B WITH(NOLOCK) ON B.NUMID      = Z.FORMULARIO_ORIGEM
                        LEFT JOIN NF_COMPRA   C WITH(NOLOCK) ON C.NF_COMPRA  = Z.REG_MASTER_ORIGEM
                        AND C.TAB_MASTER_ORIGEM = Z.TAB_MASTER_ORIGEM
                        AND C.FORMULARIO_ORIGEM = Z.FORMULARIO_ORIGEM
                        ORDER BY ORDEM
                     """;
            PreparedStatement pstmt = con.prepareStatement(sql_con);
            pstmt.setInt(1, hst.getProduto());
            pstmt.setInt(2, empresa);
            pstmt.setInt(3, hst.getProduto());
            pstmt.setInt(4, empresa);
            pstmt.setInt(5, hst.getProduto());
            ResultSet resultado = pstmt.executeQuery();
            while(resultado.next()){
                hst.setProduto(resultado.getInt("PRODUTO"));
                hst.setCusto(resultado.getDouble("CUSTO_FINAL_LIQUIDO"));
                hst.setOrdem(resultado.getInt("ORDEM"));
                hst.setDescricao(resultado.getString("DESCRICAO"));
            }
            pstmt.close();
            return hst;
        }



}
