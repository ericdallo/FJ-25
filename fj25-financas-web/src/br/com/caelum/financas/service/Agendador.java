package br.com.caelum.financas.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@AccessTimeout(unit = TimeUnit.SECONDS, value = 5)
@Singleton
public class Agendador {
	
	@Resource
	private TimerService timerService;
	
	public void agenda(String expressaoMinutos,String expressaoSegundos){
		ScheduleExpression expression = new ScheduleExpression();
		expression.hour("*").minute(expressaoMinutos).second(expressaoSegundos);
		
		TimerConfig config = new TimerConfig();
		config.setInfo(expression.toString());
		config.setPersistent(false);
		
		this.timerService.createCalendarTimer(expression,config);
		
		System.out.println("Agendamento: " + expression);
	}
	
	@Timeout
	public void executa(Timer timer) {
		System.out.println(timer.getInfo());
	}
	
	@Schedule(hour="9", minute="0", second="0", dayOfWeek="Mon", persistent=false)
	public void enviaEmailDeTeste(){
		System.out.println("Enviando email de teste");
	}

}
