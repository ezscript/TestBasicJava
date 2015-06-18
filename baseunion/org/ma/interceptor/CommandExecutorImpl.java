package org.ma.interceptor;


public class CommandExecutorImpl extends CommandInterceptor {

  public <T> T execute(Command<T> command) {
    return command.execute();
  }
}
