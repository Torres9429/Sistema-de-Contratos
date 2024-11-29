package mx.edu.utez.sistemaContratos.usuario.control;
import mx.edu.utez.sistemaContratos.usuario.model.Usuario;
import mx.edu.utez.sistemaContratos.usuario.model.UsuarioDto;
import mx.edu.utez.sistemaContratos.usuario.model.UsuarioRepository;
import mx.edu.utez.sistemaContratos.utils.CorreoSender;
import mx.edu.utez.sistemaContratos.utils.Message;
import mx.edu.utez.sistemaContratos.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UsuarioService {



    private final UsuarioRepository repository;
    private final CorreoSender correoSender;

    @Autowired
    public UsuarioService(UsuarioRepository repository, CorreoSender correoSender) {
        this.repository = repository;
        this.correoSender = correoSender;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> sendCorreo(UsuarioDto dto) {
        Optional<Usuario> optional = repository.findFirstByCorreoAndCode(dto.getCorreo(), dto.getCode());
        if(!optional.isPresent()){
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }


        Random random = new Random();
        StringBuilder numberString = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int digit = random.nextInt(10);
            numberString.append(digit);
        }
        Usuario usuario = optional.get();
        usuario.setCode(numberString.toString());
        usuario = repository.saveAndFlush(usuario);
        if(usuario == null){
            return new ResponseEntity<>(new Message("Código no registrado", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        CorreoSender.sendSimpleMessage(usuario.getCorreo(),
                "Sistema Prueba | Solicitud de restablecimiento de contraseña",
                "<!DOCTYPE html>\n" +
                        "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">\n" +
                        "\n" +
                        "<head>\n" +
                        "\t<title></title>\n" +
                        "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                        "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!-->\n" +
                        "\t<link href=\"https://fonts.googleapis.com/css?family=Cabin\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\n" +
                        "\t<style>\n" +
                        "\t\t* {\n" +
                        "\t\t\tbox-sizing: border-box;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\tbody {\n" +
                        "\t\t\tmargin: 0;\n" +
                        "\t\t\tpadding: 0;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\ta[x-apple-data-detectors] {\n" +
                        "\t\t\tcolor: inherit !important;\n" +
                        "\t\t\ttext-decoration: inherit !important;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t#MessageViewBody a {\n" +
                        "\t\t\tcolor: inherit;\n" +
                        "\t\t\ttext-decoration: none;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\tp {\n" +
                        "\t\t\tline-height: inherit\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t.desktop_hide,\n" +
                        "\t\t.desktop_hide table {\n" +
                        "\t\t\tmso-hide: all;\n" +
                        "\t\t\tdisplay: none;\n" +
                        "\t\t\tmax-height: 0px;\n" +
                        "\t\t\toverflow: hidden;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t.image_block img+div {\n" +
                        "\t\t\tdisplay: none;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\tsup,\n" +
                        "\t\tsub {\n" +
                        "\t\t\tfont-size: 75%;\n" +
                        "\t\t\tline-height: 0;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t@media (max-width:670px) {\n" +
                        "\t\t\t.image_block div.fullWidth {\n" +
                        "\t\t\t\tmax-width: 100% !important;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.mobile_hide {\n" +
                        "\t\t\t\tdisplay: none;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.row-content {\n" +
                        "\t\t\t\twidth: 100% !important;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.stack .column {\n" +
                        "\t\t\t\twidth: 100%;\n" +
                        "\t\t\t\tdisplay: block;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.mobile_hide {\n" +
                        "\t\t\t\tmin-height: 0;\n" +
                        "\t\t\t\tmax-height: 0;\n" +
                        "\t\t\t\tmax-width: 0;\n" +
                        "\t\t\t\toverflow: hidden;\n" +
                        "\t\t\t\tfont-size: 0px;\n" +
                        "\t\t\t}\n" +
                        "\n" +
                        "\t\t\t.desktop_hide,\n" +
                        "\t\t\t.desktop_hide table {\n" +
                        "\t\t\t\tdisplay: table !important;\n" +
                        "\t\t\t\tmax-height: none !important;\n" +
                        "\t\t\t}\n" +
                        "\t\t}\n" +
                        "\t</style><!--[if mso ]><style>sup, sub { font-size: 100% !important; } sup { mso-text-raise:10% } sub { mso-text-raise:-10% }</style> <![endif]-->\n" +
                        "</head>\n" +
                        "\n" +
                        "<body class=\"body\" style=\"background-color: #000000; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                        "\t<table class=\"nl-container\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #000000;\">\n" +
                        "\t\t<tbody>\n" +
                        "\t\t\t<tr>\n" +
                        "\t\t\t\t<td>\n" +
                        "\t\t\t\t\t<table class=\"row row-1\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #f3e6f8;\">\n" +
                        "\t\t\t\t\t\t<tbody>\n" +
                        "\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t<td>\n" +
                        "\t\t\t\t\t\t\t\t\t<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; background-image: url('https://d1oco4z2z1fhwp.cloudfront.net/templates/default/2971/ResetPassword_BG_2.png'); background-position: center top; background-repeat: no-repeat; color: #000000; width: 650px; margin: 0 auto;\" width=\"650\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-top: 45px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"divider_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 0px solid #BBBBBB;\"><span style=\"word-break: break-word;\">&#8202;</span></td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"20\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"alignment\" align=\"center\" style=\"line-height:10px\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"fullWidth\" style=\"max-width: 357.5px;\"><img src=\"https://d1oco4z2z1fhwp.cloudfront.net/templates/default/2971/lock5.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" width=\"357.5\" alt=\"Forgot your password?\" title=\"Forgot your password?\" height=\"auto\"></div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"heading_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-top:35px;text-align:center;width:100%;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h1 style=\"margin: 0; color: #8412c0; direction: ltr; font-family: 'Cabin', Arial, 'Helvetica Neue', Helvetica, sans-serif; font-size: 28px; font-weight: 400; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 33.6px;\"><strong>¿Olvidaste tu contraseña? Este es tú código:</strong></h1>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-left:45px;padding-right:45px;padding-top:10px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#393d47;font-family:'Cabin',Arial,'Helvetica Neue',Helvetica,sans-serif;font-size:36px;line-height:200%;text-align:center;mso-line-height-alt:72px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0; word-break: break-word;\"><span style=\"word-break: break-word; color: #aa67cf;\">"+usuario.getCode()+"</span></p>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"paragraph_block block-5\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"pad\" style=\"padding-bottom:20px;padding-left:10px;padding-right:10px;padding-top:10px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div style=\"color:#8412c0;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;font-size:14px;line-height:120%;text-align:center;mso-line-height-alt:16.8px;\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin: 0; word-break: break-word;\"><span style=\"word-break: break-word; color: #8a3b8f;\">Sistema prueba ©</span></p>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                        "\t\t\t\t\t\t\t\t\t</table>\n" +
                        "\t\t\t\t\t\t\t\t</td>\n" +
                        "\t\t\t\t\t\t\t</tr>\n" +
                        "\t\t\t\t\t\t</tbody>\n" +
                        "\t\t\t\t\t</table>\n" +
                        "\t\t\t\t</td>\n" +
                        "\t\t\t</tr>\n" +
                        "\t\t</tbody>\n" +
                        "\t</table>" +
                        "</body>\n" +
                        "\n" +
                        "</html>");

        return new ResponseEntity<>(new Message("Correo enviado", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<Object> verifyCode(UsuarioDto dto) {
        Optional<Usuario> optional = repository.findFirstByCorreoAndCode(dto.getCorreo(),dto.getCode());
        if(!optional.isPresent()){
            return new ResponseEntity<>(new Message("No se pudo verificar", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Message("Verificado", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    public Message registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getContrasena() == null) {
            throw new IllegalArgumentException("El usuario o la contraseña no pueden ser nulos");
        }

        usuario.setStatus(true); // Habilitar usuario por defecto
        repository.save(usuario);
        return new Message(usuario, "Usuario registrado correctamente", TypesResponse.SUCCESS);
    }

    public List<Usuario> consultarUsuarios() {
        return repository.findAll();
    }

    public Message editarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setApellidos(usuarioActualizado.getApellidos());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
        usuarioExistente.setStatus(usuarioActualizado.isStatus());

        repository.save(usuarioExistente);

        return new Message("Usuario actualizado correctamente", TypesResponse.SUCCESS);
    }

    public Message cambiarEstado(Long id, boolean status) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        usuario.setStatus(status);
        repository.save(usuario);
        return new Message(usuario, "Estado actualizado correctamente", TypesResponse.SUCCESS);
    }

    public Message iniciarSesion(String correo, String contrasena) {
        // Verificar si el usuario existe
        Usuario usuario = (Usuario) repository.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        // Validar la contraseña
        if (!contrasena.equals(usuario.getContrasena())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        return new Message("Inicio de sesión exitoso", TypesResponse.SUCCESS); // Devolver el token
    }

    public Message cerrarSesion() {
        // En JWT, simplemente se elimina el token en el lado del cliente
        return new Message("Sesión cerrada correctamente", TypesResponse.SUCCESS);
    }
}

