/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.springframework.stereotype.Controller;
import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import static com.amazonaws.services.s3.model.CryptoStorageMode.ObjectMetadata;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import dao.EventoDAO;
import dao.SitioDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Sitio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Edgar-Mac
 */
@Controller("ImageController")
public class ImageController {

    @Autowired
    @Qualifier("Usuario")
    private UsuarioDAO usuarioDAO;

    @Autowired
    @Qualifier("Sitio")
    private SitioDAO sitioDAO;
    
    @Autowired
    @Qualifier("Evento")
    private EventoDAO eventoDAO;

    @RequestMapping(method = RequestMethod.POST, value = "/upload_picture_profile")
    public String uploadPhotoProfile(@RequestParam("file") MultipartFile file, HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            String imageKey = "image-" + UUID.randomUUID();
            String uploadPath = "images/user/ " + imageKey;
            String s3bucketName = "storagedinor";

            AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIW7LRHGVAOQTGIBA", "E95S4zdlkMRQiLneBZtRPlaky3HwQreSBRxFoNE/"));
            try {
                InputStream is = file.getInputStream();
                String existe = usuarioDAO.consultaImagenPerfil((int) session.getAttribute("idUsuario"));
                String directorio = "images/user/ " + existe;
                if (!existe.equals("")) {
                    s3client.deleteObject(new DeleteObjectRequest(s3bucketName, directorio));
                }
                System.out.println("Uploading a new object to S3 from a file\n");
                s3client.putObject(new PutObjectRequest(
                        s3bucketName, uploadPath, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

                if (existe.equals("")) {
                    usuarioDAO.agregarImagenPerfil(imageKey, (int) session.getAttribute("idUsuario"));
                } else {
                    usuarioDAO.actualizaImagenPerfil(imageKey, (int) session.getAttribute("idUsuario"));
                }

                S3Object s3Object = s3client.getObject(new GetObjectRequest(s3bucketName, imageKey));

                model.addAttribute("imagenPerfil", s3Object.getObjectContent().getHttpRequest().getURI().toString());

            } catch (AmazonServiceException ase) {
                System.out.println("Caught an AmazonServiceException, which "
                        + "means your request made it "
                        + "to Amazon S3, but was rejected with an error response"
                        + " for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {
                System.out.println("Caught an AmazonClientException, which "
                        + "means the client encountered "
                        + "an internal error while trying to "
                        + "communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "perfil";

        } else {
            return "inicio";

        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload_picture_place")
    public String uploadPhotoPlace(@RequestParam("file") MultipartFile file, HttpServletRequest request, ModelMap model,RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            String imageKey = "image-" + UUID.randomUUID();
            String uploadPath = "images/places/ " + imageKey;
            String s3bucketName = "storagedinor";
            int idSitio = parseInt(request.getParameter("idSitio"));

            AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIW7LRHGVAOQTGIBA", "E95S4zdlkMRQiLneBZtRPlaky3HwQreSBRxFoNE/"));
            try {
                InputStream is = file.getInputStream();
                System.out.println("Uploading a new object to S3 from a file\n");
                s3client.putObject(new PutObjectRequest(
                        s3bucketName, uploadPath, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

                sitioDAO.agregarImagenSitio(imageKey, idSitio);

            } catch (AmazonServiceException ase) {
                System.out.println("Caught an AmazonServiceException, which "
                        + "means your request made it "
                        + "to Amazon S3, but was rejected with an error response"
                        + " for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {
                System.out.println("Caught an AmazonClientException, which "
                        + "means the client encountered "
                        + "an internal error while trying to "
                        + "communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectAttributes.addAttribute("idSitio", idSitio);
            redirectAttributes.addAttribute("latitud", request.getParameter("latitud"));
            redirectAttributes.addAttribute("longitud", request.getParameter("longitud"));
            return "redirect:/modificar_sitio";
            

        } else {
            return "inicio";

        }
    }
    
    @RequestMapping("/delete_picture_place")
    public String deletePhotoPlace( HttpServletRequest request, ModelMap model,RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            String imageKey = request.getParameter("image");
            String uploadPath = "images/places/ " + imageKey;
            String s3bucketName = "storagedinor";
            int idSitio = parseInt(request.getParameter("idSitio"));

            AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIW7LRHGVAOQTGIBA", "E95S4zdlkMRQiLneBZtRPlaky3HwQreSBRxFoNE/"));
            try {
                s3client.deleteObject(new DeleteObjectRequest(
                        s3bucketName, uploadPath));

                sitioDAO.eliminarImagenSitio(imageKey, idSitio);

            } catch (AmazonServiceException ase) {
                System.out.println("Caught an AmazonServiceException, which "
                        + "means your request made it "
                        + "to Amazon S3, but was rejected with an error response"
                        + " for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {
                System.out.println("Caught an AmazonClientException, which "
                        + "means the client encountered "
                        + "an internal error while trying to "
                        + "communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            }
            redirectAttributes.addAttribute("idSitio", idSitio);
            return "redirect:/modificar_sitio";
            

        } else {
            return "inicio";

        }
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/upload_picture_event")
    public String uploadPhotoEvent(@RequestParam("file") MultipartFile file, HttpServletRequest request, ModelMap model,RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            String imageKey = "image-" + UUID.randomUUID();
            String uploadPath = "images/events/ " + imageKey;
            String s3bucketName = "storagedinor";
            int idEvento = parseInt(request.getParameter("idEvento"));

            AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIW7LRHGVAOQTGIBA", "E95S4zdlkMRQiLneBZtRPlaky3HwQreSBRxFoNE/"));
            try {
                InputStream is = file.getInputStream();
                System.out.println("Uploading a new object to S3 from a file\n");
                s3client.putObject(new PutObjectRequest(
                        s3bucketName, uploadPath, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));

                eventoDAO.agregarImagenEvento(imageKey, idEvento);

            } catch (AmazonServiceException ase) {
                System.out.println("Caught an AmazonServiceException, which "
                        + "means your request made it "
                        + "to Amazon S3, but was rejected with an error response"
                        + " for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {
                System.out.println("Caught an AmazonClientException, which "
                        + "means the client encountered "
                        + "an internal error while trying to "
                        + "communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
            }
            redirectAttributes.addAttribute("idEvento", idEvento);
            redirectAttributes.addAttribute("latitud", request.getParameter("latitud"));
            redirectAttributes.addAttribute("longitud", request.getParameter("longitud"));
            return "redirect:/modificar_evento";
            

        } else {
            return "inicio";

        }
    }
    
    @RequestMapping("/delete_picture_event")
    public String deletePhotoEvent( HttpServletRequest request, ModelMap model,RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            String imageKey = request.getParameter("image");
            String uploadPath = "images/places/ " + imageKey;
            String s3bucketName = "storagedinor";
            int idEvento = parseInt(request.getParameter("idEvento"));

            AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAIW7LRHGVAOQTGIBA", "E95S4zdlkMRQiLneBZtRPlaky3HwQreSBRxFoNE/"));
            try {
                s3client.deleteObject(new DeleteObjectRequest(
                        s3bucketName, uploadPath));

                eventoDAO.eliminarImagenEvento(imageKey, idEvento);

            } catch (AmazonServiceException ase) {
                System.out.println("Caught an AmazonServiceException, which "
                        + "means your request made it "
                        + "to Amazon S3, but was rejected with an error response"
                        + " for some reason.");
                System.out.println("Error Message:    " + ase.getMessage());
                System.out.println("HTTP Status Code: " + ase.getStatusCode());
                System.out.println("AWS Error Code:   " + ase.getErrorCode());
                System.out.println("Error Type:       " + ase.getErrorType());
                System.out.println("Request ID:       " + ase.getRequestId());
            } catch (AmazonClientException ace) {
                System.out.println("Caught an AmazonClientException, which "
                        + "means the client encountered "
                        + "an internal error while trying to "
                        + "communicate with S3, "
                        + "such as not being able to access the network.");
                System.out.println("Error Message: " + ace.getMessage());
            }
            redirectAttributes.addAttribute("idEvento", idEvento);
            return "redirect:/modificar_evento";
            

        } else {
            return "inicio";

        }
    }
}
