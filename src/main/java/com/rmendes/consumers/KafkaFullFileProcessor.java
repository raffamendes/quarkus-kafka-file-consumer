package com.rmendes.consumers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.kafka.Record;




@ApplicationScoped
public class KafkaFullFileProcessor {

	@Inject
	@Channel("line-receiver")
	Emitter<Record<String, String>> lineEmmiter;
	
	private static final Character LINE_IDENTIFIER_R = 'R';
	
	private static final Character LINE_IDENTIFIER_P = 'P';

	private static final Character LINE_IDENTIFIER_Q = 'Q';		

	/*
	 * @Incoming("file-full") public void consumeFullFile(String fullFile) {
	 * Arrays.asList(fullFile.split("\n")).stream(). forEach(s -> {
	 * System.out.println(s); lineEmmiter.send(Message.of(s) .withAck(() -> { return
	 * CompletableFuture.completedFuture(null); }) .withNack(throwable ->{ return
	 * CompletableFuture.completedFuture(null); })); });
	 * 
	 * }
	 */

	@Incoming("file-full-240")
	public void consumeFullFile(Record<String, String> fullFile) {
		String keyPart = fullFile.key()+LocalDate.now();
		List<String> lines = Arrays.asList(fullFile.value().split("\\n", -1)).stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
		for(int i =0; i < lines.size(); i++) {
			if(lines.get(i).contains("REMESSA")) {
				lineEmmiter.send(Record.of(keyPart+"HF", lines.get(i)));
			}else if(LINE_IDENTIFIER_R.compareTo(lines.get(i).charAt(8)) == 0) {
				lineEmmiter.send(Record.of(keyPart+"HL", lines.get(i)));
								
			}else if(LINE_IDENTIFIER_P.compareTo(lines.get(i).charAt(13)) == 0) {
				lineEmmiter.send(Record.of(keyPart+"PQ", lines.get(i)+lines.get(i+1)));
				i++;
			}
		}
	}

}
