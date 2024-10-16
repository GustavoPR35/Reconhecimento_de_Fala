package teste2str;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/*
 * Código quickstart de reconhecimento POR MICROFONE disponível em: https://learn.microsoft.com/en-us/azure/ai-services/speech-service/get-started-speech-to-text?tabs=linux%2Cterminal&pivots=programming-language-java
 * Algumas alterações foram feitas
 * 
 * Se quiser usar outros exemplos, tem vários arquivos .wav no site kaggle.
 */

public class SpeechRecognition {
    // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    private static String speechKey = ""; // Coloque sua própria chave do recurso do azure aqui (Azure AI services, Speech service)
    private static String speechRegion = ""; // Coloque sua própria região do recurso do azure aqui (Azure AI services, Speech service)

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        
        // ALTERE O IDIOMA AQUI!
        // Opções disponíveis em: https://learn.microsoft.com/en-us/azure/ai-services/speech-service/language-support?tabs=stt
        speechConfig.setSpeechRecognitionLanguage("en-US");
        
        // Use a linha abaixo se quiser testar com microfone
        //recognizeFromMicrophone(speechConfig);
        
        // Se for usar a versão de reconhecer de arquivos .wav, mude o caminho aqui
        String audioFilePath = "/home/gustavo/Downloads/harvard.wav";
        recognizeFromWavFile(speechConfig, audioFilePath);
    }

    public static void recognizeFromMicrophone(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
    	try (SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig)) {
			System.out.println("Speak into your microphone.");
			Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
			SpeechRecognitionResult speechRecognitionResult = task.get();

			if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
			    System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
			}
			else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
			    System.out.println("NOMATCH: Speech could not be recognized.");
			}
			else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
			    CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
			    System.out.println("CANCELED: Reason=" + cancellation.getReason());

			    if (cancellation.getReason() == CancellationReason.Error) {
			        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
			        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
			        System.out.println("CANCELED: Did you set the speech resource key and region values?");
			    }
			}
		}

        System.exit(0);
    }
    
    public static void recognizeFromWavFile(SpeechConfig speechConfig, String audioFilePath) throws InterruptedException, ExecutionException {
        // Configura o áudio a partir do arquivo wav
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(audioFilePath);
        try (SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig)) {
			System.out.println("Reconhecendo de arquivo .wav, aguarde...");
			Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
			SpeechRecognitionResult speechRecognitionResult = task.get();

			// Exibe o resultado
			if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
			    System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
			}
			else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
			    System.out.println("NOMATCH: Speech could not be recognized.");
			}
			else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
			    CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
			    System.out.println("CANCELED: Reason=" + cancellation.getReason());

			    if (cancellation.getReason() == CancellationReason.Error) {
			        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
			        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
			        System.out.println("CANCELED: Did you set the speech resource key and region values?");
			    }
			}
		}

        System.exit(0);
    }
    
}