package com.sundevils.web.controller;

import handlers.externaluserHandlers.FileUpload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import utilities.SendEmail;

@Controller
public class FileUploadController {

	@RequestMapping(value = "/upload", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView crunchifySave(HttpServletRequest request,HttpServletResponse response,HttpSession session,
			@ModelAttribute("uploadForm") FileUpload uploadForm,
			Model map ) throws IllegalStateException, IOException {
		boolean success = false;
		ModelAndView model = new ModelAndView();

		if(session.getAttribute("registered") == null){
			model.setViewName("index");
			return model;
		}
		
		List<MultipartFile> crunchifyFiles = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();

		if (null != crunchifyFiles && crunchifyFiles.size() > 0) {
			for (MultipartFile multipartFile : crunchifyFiles) {

				String fileName = multipartFile.getOriginalFilename();
				try{
					
					String ssnvalue = (String)session.getAttribute("SSN_Value");
					//System.out.println(ssnvalue);
					String saveDirectory = "C:/AccountOpeningDocuments/" + ssnvalue + "/";
					File directory = new File(saveDirectory); 
					if (directory.exists()) 
					{ 
						System.out.println("Directory already exists ..."); 
					} 
					else 
					{ 
						System.out.println("Directory not exists, creating now"); 
						success = directory.mkdirs(); 
						if (success)
						{ 
							System.out.printf("Successfully created new directory : %s%n", saveDirectory);
						} 
						else 
						{ 
							System.out.printf("Failed to create new directory: %s%n", saveDirectory); 
						} 
					}
					if (!"".equalsIgnoreCase(fileName)) {
						
						String path = saveDirectory + fileName;
                        multipartFile
						.transferTo(new File(saveDirectory + fileName));
						fileNames.add(fileName);
						PdfReader pdfReader = new PdfReader( path );  

					    String textFromPdfFilePageOne = PdfTextExtractor.getTextFromPage( pdfReader, 1 ); 
					    System.out.println( textFromPdfFilePageOne );
						String firstname= (String)session.getAttribute("CUSTOMER_NAME");
						String accounttype = (String)session.getAttribute("ACCOUNT_TYPE");
						String email= (String)session.getAttribute("CUSTOMER_EMAIL");
						SendEmail sendemail=new SendEmail();
						sendemail.sendEmailApplication(firstname, "" , accounttype, email);
						
						
						map.addAttribute("files", fileNames);
						
						model.setViewName("uploadfilesuccess");
					}

				}
				catch(Exception e){
					model.addObject("error", "Not a pdf file. Please change the file to pdf and upload");
					model.setViewName("uploadfile");

				}
			}
		}
				else
				{ 
					model.addObject("Successful", " Please upload files");
					model.setViewName("uploadfile");
				}
		session.removeAttribute("registered");
		return model;
	}
}

