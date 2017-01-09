package com.mama.server.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This utility class provides an abstraction layer for sending multipart HTTP POST requests to a web server.
 */
public class MultipartUtility
{
    private final String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private String charset;
    private OutputStream outputStream;
    private PrintWriter writer;

    private int connetTimeOutDefualt = 10*1000;
    private int readTimeOutDefualt = 10*60*1000;
    /**
     * This constructor initializes a new HTTP POST request with content type is set to multipart/form-data
     * 
     * @param requestURL
     * @param charset
     * @throws IOException
     */
    public MultipartUtility(String requestURL, String charset) throws IOException
    {
        this.charset = charset;
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setConnectTimeout(connetTimeOutDefualt);
        httpConn.setReadTimeout(readTimeOutDefualt);
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
    }
    public MultipartUtility(String requestURL, String charset,int connectTimeOut, int readTimeOut ) throws IOException
    {
        this.charset = charset;
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setConnectTimeout(connectTimeOut);
        httpConn.setReadTimeout(readTimeOut);
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
    }
    /**
     * Adds a form field to the request
     * 
     * @param name
     *            field name
     * @param value
     *            field value
     */
    public void addFormField(String name, String value)
    {
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Adds a upload file section to the request
     * 
     * @param fieldName
     *            name attribute in <input type="file" name="..." />
     * @param uploadFile
     *            a File to be uploaded
     * @throws FileNotFoundException 
     * @throws IOException
     */
    public void addFilePart(String fieldName, File uploadFile) throws IOException
    {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();
        FileInputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(uploadFile);
         
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            System.out.println("[addFilePart]filesize = " + outputStream.toString().length());
            writer.append(LINE_FEED);
            writer.flush();
        }
        catch(IOException e)
        {
            throw e;
        }
        finally
        {
            closeOutputStream(outputStream);
            closeInputStream(inputStream);
        }
    }

    /**
     * Adds a header field to the request.
     * 
     * @param name
     *            - name of the header field
     * @param value
     *            - value of the header field
     */
    public void addHeaderField(String name, String value)
    {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Completes the request and receives response from the server.
     * 
     * @return a list of Strings as response in case the server returned status OK, otherwise an exception is thrown.
     * @throws IOException
     */
    public String finish() throws IOException,SocketTimeoutException
    {
        StringBuilder response = new StringBuilder();
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        
        BufferedReader  reader = null;
        try
        {
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK)
            {
                reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    response.append(line);
                }
            }
            else
            {
                System.out.println("Server returned non-OK status: " + status);
            }   
        }
        catch(SocketTimeoutException e)
        {
            throw e;
        }
        finally
        {
            closeReader(reader);
            
            if(httpConn !=null)
                httpConn.disconnect();
            
            closePrintWriter(writer);
        }
        return response.toString();
    }

    private void closeReader(BufferedReader  reader)
    {
        try
        {
            if(reader !=null)
                reader.close();
        }
        catch (IOException ignore)
        { }
    }
    private void closePrintWriter(PrintWriter writer)
    {
        if(writer != null)
        {
            writer.close();
        }
    }
    private void closeOutputStream(OutputStream  os)
    {
        try
        {
            if(os !=null)
                os.close();
        }
        catch (IOException ignore)
        { }
    }
    private void closeInputStream(InputStream  is)
    {
        try
        {
            if(is !=null)
                is.close();
        }
        catch (IOException ignore)
        { }
    }
    
    public static void main(String[] args)
    {

    }
}
