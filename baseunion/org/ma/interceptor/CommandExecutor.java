package org.ma.interceptor;




public interface CommandExecutor {

  <T> T execute(Command<T> command);

}
