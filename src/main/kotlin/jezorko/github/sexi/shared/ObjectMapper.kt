package jezorko.github.sexi.shared

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

val objectMapper = ObjectMapper().registerModule(KotlinModule())!!