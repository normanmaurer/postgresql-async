/*
 * Copyright 2013 Maurício Linhares
 *
 * Maurício Linhares licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.github.mauricio.async.db.postgresql.codec

import com.github.mauricio.async.db.column.ColumnEncoderRegistry
import com.github.mauricio.async.db.exceptions.EncoderNotAvailableException
import com.github.mauricio.async.db.postgresql.encoders._
import com.github.mauricio.async.db.postgresql.messages.backend.ServerMessage
import com.github.mauricio.async.db.postgresql.messages.frontend._
import com.github.mauricio.async.db.util.Log
import java.nio.charset.Charset
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel.{Channel, ChannelHandlerContext}
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder
import scala.annotation.switch

object MessageEncoder {
  val log = Log.get[MessageEncoder]
}

class MessageEncoder(charset: Charset, encoderRegistry: ColumnEncoderRegistry) extends OneToOneEncoder {

  private val executeEncoder = new ExecutePreparedStatementEncoder(charset, encoderRegistry)
  private val openEncoder = new PreparedStatementOpeningEncoder(charset, encoderRegistry)
  private val startupEncoder = new StartupMessageEncoder(charset)
  private val queryEncoder = new QueryMessageEncoder(charset)
  private val credentialEncoder = new CredentialEncoder(charset)

  override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: AnyRef): ChannelBuffer = {

    val buffer = msg match {
      case message: ClientMessage => {
        val encoder = (message.kind : @switch) match {
          case ServerMessage.Close => CloseMessageEncoder
          case ServerMessage.Execute => this.executeEncoder
          case ServerMessage.Parse => this.openEncoder
          case ServerMessage.Startup => this.startupEncoder
          case ServerMessage.Query => this.queryEncoder
          case ServerMessage.PasswordMessage => this.credentialEncoder
          case _ => throw new EncoderNotAvailableException(message)
        }
        encoder.encode(message)
      }
      case _ => {
        throw new IllegalArgumentException("Can not encode message %s".format(msg))
      }
    }

    buffer
  }

}
