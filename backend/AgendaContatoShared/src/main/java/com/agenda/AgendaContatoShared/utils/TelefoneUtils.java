package com.agenda.AgendaContatoShared.utils;

import com.agenda.AgendaContatoShared.exception.TelefoneInvalido;

import java.util.Map;

public class TelefoneUtils {

    private static Map<String, String> codigoDDDPorEstadoBR = Map.ofEntries(
            Map.entry("^(1[1-9])", "São Paulo"),
            Map.entry("^(21|22|24)", "Rio de Janeiro"),
            Map.entry("^(27|28)", "Espirito Santo"),
            Map.entry("^(3[1-5]|37|38)", "Minas Gerais"),
            Map.entry("^(4[1-6])", "Paraná"),
            Map.entry("^(4[7-9])", "Santa Catarina"),
            Map.entry("^(5[1|3-5])", "Rio Grande do Sul"),
            Map.entry("^(61)", "Distrito Federal/Goiás"),
            Map.entry("^(62|64)", "Goiás"),
            Map.entry("^(63)", "Tocantins"),
            Map.entry("^(65|66)", "Mato Grosso"),
            Map.entry("^(67)", "Mato Grosso do Sul"),
            Map.entry("^(68)", "Acre"),
            Map.entry("^(69)", "Rondônia"),
            Map.entry("^(7[1|3-5|7])", "Bahia"),
            Map.entry("^(79)", "Sergipe"),
            Map.entry("^(81|87)", "Pernambuco"),
            Map.entry("^(82)", "Alagoas"),
            Map.entry("^(83)", "Paraíba"),
            Map.entry("^(84)", "Rio Grande do Norte"),
            Map.entry("^(85|88)", "Ceará"),
            Map.entry("^(86|89)", "Piauí"),
            Map.entry("^(9[1|3|4])", "Pará"),
            Map.entry("^(92|97)", "Amazonas"),
            Map.entry("^(95)", "Roraima"),
            Map.entry("^(96)", "Amapá"),
            Map.entry("^(98|99)", "Maranhão")
    );

    private static String REGEX_NUMERO_LIMPO = "^(\\d{2})(9)(\\d{4})(\\d{4})$";

    public static String formatarCelular(String telefone) {
        if (telefone == null || telefone.isBlank() || telefone.isEmpty())
            throw new TelefoneInvalido("Telefone informado inválido!! Informe um telefone válido.");

        String numeroLimpo = telefone.replaceAll("\\D+", "");

        if (!numeroLimpo.matches(REGEX_NUMERO_LIMPO))
            throw new TelefoneInvalido("Telefone informado inválido!! Valor informado: " + telefone + ". Estrutura exigida: xx9xxxxxxxx, onde o 'x' deve ser um número");

        String ddd = numeroLimpo.substring(0, 2);
        boolean dddValido = false;

        for (String regexDDD : codigoDDDPorEstadoBR.keySet()) {
            if (ddd.matches(regexDDD)) {
                dddValido = true;
                break;
            }
        }

        if (!dddValido)
            throw new TelefoneInvalido("Telefone informado inválido!! O DDD " + ddd + " não pertence a nenhum estado brasileiro.");

        return numeroLimpo.replaceAll(REGEX_NUMERO_LIMPO, "($1) $2 $3-$4");
    }

//    public static void main(String[] args) {
//        String t = "83991717654";
//        String novoT = formatarCelular(t);
//        System.out.println(novoT);
//    }
}
